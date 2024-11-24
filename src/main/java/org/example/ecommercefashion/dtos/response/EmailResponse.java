package org.example.ecommercefashion.dtos.response;

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
