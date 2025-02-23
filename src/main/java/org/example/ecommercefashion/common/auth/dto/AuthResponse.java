package org.example.ecommercefashion.common.auth.dto;

import lombok.Builder;


@Builder
public record AuthResponse(

        String accessToken,

        String refreshToken
) {
}
