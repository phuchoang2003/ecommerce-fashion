package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import org.example.ecommercefashion.entities.postgres.ResetPasswordToken;
import org.example.ecommercefashion.entities.postgres.User;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.ResetTokenPasswordRepository;
import org.example.ecommercefashion.services.ResetPasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class ResetPasswordResetTokenServiceImpl implements ResetPasswordResetTokenService {
    @Value("${reset_token.expiration}")
    private long expirationTime;

    @Autowired
    private ResetTokenPasswordRepository resetTokenPasswordRepository;


    @Override
    public ResetPasswordToken findByToken(String token) {
        return resetTokenPasswordRepository.findToken(token).orElseThrow(() -> new ExceptionHandle(HttpStatus.NOT_FOUND, ErrorMessage.RESET_TOKEN_NOT_FOUND.val()));
    }

    @Override
    public ResetPasswordToken createToken(User user) {
        ResetPasswordToken resetPasswordToken = ResetPasswordToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .userId(user.getId())
                .expirationAt(new Timestamp(System.currentTimeMillis() + expirationTime))
                .isUsed(false)
                .build();

        return resetTokenPasswordRepository.save(resetPasswordToken);
    }
}
