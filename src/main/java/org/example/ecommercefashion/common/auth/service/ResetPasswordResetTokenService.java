package org.example.ecommercefashion.common.auth.service;

import org.example.ecommercefashion.common.auth.entity.ResetPasswordToken;
import org.example.ecommercefashion.module.user.entity.User;

public interface ResetPasswordResetTokenService {
    ResetPasswordToken createToken(User user);

    ResetPasswordToken findByToken(String token);

}
