package org.example.ecommercefashion.common.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.ecommercefashion.common.auth.dto.LoginRequest;
import org.example.ecommercefashion.common.auth.dto.ResetPasswordRequest;
import org.example.ecommercefashion.module.user.dto.UserRequest;
import org.example.ecommercefashion.common.auth.dto.AuthResponse;
import org.example.ecommercefashion.common.auth.dto.LoginResponse;
import org.example.ecommercefashion.module.chat.dto.MessageResponse;
import org.example.ecommercefashion.module.user.dto.UserResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

    MessageResponse requestResetPassword(ResetPasswordRequest request);

    UserResponse signUp(UserRequest userRequest);

    MessageResponse resetPassword(String newPassword, String token);

    AuthResponse refreshToken(String refreshToken, HttpServletRequest request);
}
