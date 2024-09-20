package org.example.ecommercefashion.services;

import org.example.ecommercefashion.entities.postgres.ResetPasswordToken;
import org.example.ecommercefashion.entities.postgres.User;

public interface ResetPasswordResetTokenService {
    ResetPasswordToken createToken(User user);

    ResetPasswordToken findByToken(String token);

}
