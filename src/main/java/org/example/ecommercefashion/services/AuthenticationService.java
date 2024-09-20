package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.LoginRequest;
import org.example.ecommercefashion.dtos.request.ResetPasswordRequest;
import org.example.ecommercefashion.dtos.request.UserRequest;
import org.example.ecommercefashion.dtos.response.AuthResponse;
import org.example.ecommercefashion.dtos.response.LoginResponse;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.UserResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest, HttpServletRequest request);

    MessageResponse requestResetPassword(ResetPasswordRequest request);

    UserResponse signUp(UserRequest userRequest);

    MessageResponse resetPassword(String newPassword, String token);

    AuthResponse refreshToken(String refreshToken, HttpServletRequest request);
}
