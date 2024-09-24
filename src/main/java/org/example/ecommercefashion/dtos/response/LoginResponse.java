package org.example.ecommercefashion.dtos.response;

import lombok.Builder;


@Builder
public record LoginResponse(

        AuthResponse authResponse,

        UserResponse userResponse
) {
}
