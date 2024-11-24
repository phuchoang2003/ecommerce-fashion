package org.example.ecommercefashion.services.impl;


import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.EmailTemplateRequest;
import org.example.ecommercefashion.dtos.response.ResponsePage;
import org.example.ecommercefashion.entities.postgres.EmailPlaceHolder;
import org.example.ecommercefashion.entities.postgres.EmailTemplate;
import org.example.ecommercefashion.enums.EmailTemplateEnums;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.EmailPlaceHolderRepository;
import org.example.ecommercefashion.repositories.postgres.EmailTemplateRepository;
import org.example.ecommercefashion.services.EmailTemplateService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailPlaceHolderRepository emailPlaceHolderRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chooseEmailTemplate(Long id) {
        EmailTemplate emailTemplate = get(id);
        EmailTemplate existTemplate = emailTemplateRepository
                .findByTemplateNameAndIsActive(emailTemplate.getTemplateName(), true)
                .orElse(null);

        // tắt cái dang active thành false
        if (existTemplate != null) existTemplate.setIsActive(false);
        emailTemplate.setIsActive(true);

    }

    @Override
    public void existEmailTemplate(EmailTemplateEnums templateName) {
        boolean isExist = emailTemplateRepository.existsByTemplateName(templateName);
        if (!isExist) throw new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.EMAIL_TEMPLATE_NOT_FOUND.val());
    }

    @Override
    public List<EmailTemplate> getEmailTemplateBy(EmailTemplateEnums templateName) {
        return emailTemplateRepository
                .findByTemplateName(templateName);
    }


    private void checkVariables(Set<String> variables) {

        Set<String> placeHolderNames = emailPlaceHolderRepository.findByPlaceHolderNameIn(variables)
                .stream()
                .map(EmailPlaceHolder::getPlaceHolderName)
                .collect(Collectors.toSet());
        for (var variable : variables) {
            if (!placeHolderNames.contains(variable)) {
                throw new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.EMAIL_TEMPLATE_VARIABLE_NOT_FOUND.val() + variable);
            }
        }
    }

    @Override
    public EmailTemplate create(EmailTemplateRequest request) {
        checkVariables(request.getVariables());
        EmailTemplate newTemplate = EmailTemplate.fromRequest(request);
        List<EmailTemplate> templates = getEmailTemplateBy(request.getTemplateName());

        templates.stream()
                .max(Comparator.comparingInt(EmailTemplate::getVersion))
                .ifPresent(existTemplate -> newTemplate.setVersion(existTemplate.getVersion() + 1));

        return emailTemplateRepository.save(newTemplate);
    }


    @Override
    public void delete(Long id) {
        EmailTemplate emailTemplate = get(id);
        emailTemplate.setDeleted(true);
        emailTemplate.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        emailTemplateRepository.save(emailTemplate);
    }

    @Override
    public EmailTemplate get(Long id) {
        return emailTemplateRepository
                .findById(id)
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.EMAIL_TEMPLATE_NOT_FOUND.val()));
    }

    @Override
    public ResponsePage<EmailTemplate, EmailTemplate> getAllTemplate(Pageable pageable) {
        return new ResponsePage<>(emailTemplateRepository.findAll(pageable));
    }
}
