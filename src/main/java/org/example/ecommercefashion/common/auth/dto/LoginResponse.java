package org.example.ecommercefashion.common.auth.dto;

import lombok.Builder;
import org.example.ecommercefashion.module.user.dto.UserResponse;


@Builder
public record LoginResponse(

        AuthResponse authResponse,

        UserResponse userResponse
) {
}
