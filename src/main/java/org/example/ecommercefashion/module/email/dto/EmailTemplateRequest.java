package org.example.ecommercefashion.module.email.dto;

import lombok.*;
import org.example.ecommercefashion.common.core.annotation.EnumPattern;
import org.example.ecommercefashion.module.email.enums.EmailTemplateEnums;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailTemplateRequest {
    @EnumPattern(regexp = "ORDER_SUCCESS|ORDER_FAILED|ORDER_CANCEL|CUSTOMER_SIGN_UP", name = "templateName")
    private EmailTemplateEnums templateName;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotBlank(message = "subject cannot be blank")
    private String subject;

    @NotBlank(message = "body cannot be blank")
    private String body;

    @NotNull(message = "variables cannot be null")
    private Set<String> variables;

}
