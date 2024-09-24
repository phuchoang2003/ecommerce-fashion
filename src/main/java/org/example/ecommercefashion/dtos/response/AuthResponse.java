package org.example.ecommercefashion.dtos.response;

import lombok.Builder;


@Builder
public record AuthResponse(

        String accessToken,

        String refreshToken
) {
}
