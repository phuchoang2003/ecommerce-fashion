package org.example.ecommercefashion.module.email.service;

import org.example.ecommercefashion.module.email.dto.EmailTemplateRequest;
import org.example.ecommercefashion.common.core.dto.ResponsePage;
import org.example.ecommercefashion.module.email.enums.EmailTemplateEnums;
import org.example.ecommercefashion.module.email.entity.EmailTemplate;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmailTemplateService {
    // create
    EmailTemplate create(EmailTemplateRequest request);

    // get
    EmailTemplate get(Long id);


    void existEmailTemplate(EmailTemplateEnums templateName);

    // getEmailTemplatebyTemplate
    List<EmailTemplate> getEmailTemplateBy(EmailTemplateEnums templateName);

    // get all
    ResponsePage<EmailTemplate, EmailTemplate> getAllTemplate(Pageable pageable);

    // delete
    void delete(Long id);

    // setting config
    void chooseEmailTemplate(Long id);


}
