package org.example.ecommercefashion.module.email.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.module.email.entity.Email;
import org.example.ecommercefashion.module.email.entity.EmailTemplate;
import org.example.ecommercefashion.module.email.service.EmailService;
import org.example.ecommercefashion.module.email.service.EmailTemplateService;
import org.example.ecommercefashion.module.email.repository.EmailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String UNDERSCORE = "_";
    private static final String ID = "ID";
    private static final String OPEN_BRACKET = "[";
    private static final String CLOSE_BRACKET = "]";
    private final EmailRepository emailRepository;
    private final EmailTemplateService emailTemplateService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Email createEmail(Long idTemplate, String sendTo, String sendFrom, Object object) {
        EmailTemplate template = emailTemplateService.get(idTemplate);
        Email email = replaceEmailPlaceHolder(template, sendTo, sendFrom, object);

        return emailRepository.save(email);
    }

    private Email replaceEmailPlaceHolder(EmailTemplate template, String sendTo, String sendFrom, Object object) {
        return Email.builder()
                .emailTemplate(template)
                .sendFrom(sendFrom)
                .sendTo(sendTo)
                .body(processTemplate(template.getBody(), object, template.getVariables()))
                .subject(processTemplate(template.getSubject(), object, template.getVariables()))
                .build();
    }

    private String processTemplate(String template, Object obj, List<String> variables) {
        try {
            for (String variable : variables) {
                String camelCaseVariableName = convertToCamelCase(variable);
                Field field = null;
                try {
                    if (camelCaseVariableName.substring(camelCaseVariableName.length() - 2).equalsIgnoreCase(ID)) {
                        camelCaseVariableName = ID.toLowerCase();
                        field = obj.getClass().getSuperclass().getDeclaredField(camelCaseVariableName);
                        System.out.println(field.getName());
                    } else {
                        field = obj.getClass().getDeclaredField(camelCaseVariableName);
                    }
                } catch (NoSuchFieldException e) {
                    field = obj.getClass().getSuperclass().getDeclaredField(camelCaseVariableName);
                }

                field.setAccessible(true);
                Object value = field.get(obj);

                if (value != null) {
                    template = template.replace(OPEN_BRACKET + variable.toUpperCase() + CLOSE_BRACKET, value.toString());
                }
            }
        } catch (NoSuchFieldException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return template;
    }

    private String convertToCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        String[] parts = input.split("_");

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                if (i == 0) {
                    result.append(parts[i].toLowerCase());
                } else {
                    result.append(Character.toUpperCase(parts[i].charAt(0)));
                    result.append(parts[i].substring(1).toLowerCase());
                }
            }
        }

        return result.toString();
    }

}
