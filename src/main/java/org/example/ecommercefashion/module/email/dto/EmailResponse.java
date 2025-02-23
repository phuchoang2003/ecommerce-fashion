package org.example.ecommercefashion.module.email.dto;

import lombok.Builder;

@Builder
public record EmailResponse(
        String body,
        String subject,
        String sendTo,
        String sendFrom,
        String sendAt
) {

}
