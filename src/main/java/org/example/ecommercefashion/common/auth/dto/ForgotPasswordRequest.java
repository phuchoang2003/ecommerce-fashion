package org.example.ecommercefashion.common.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommercefashion.common.core.annotation.ValidPassword;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    @ValidPassword
    private String newPassword;
}
