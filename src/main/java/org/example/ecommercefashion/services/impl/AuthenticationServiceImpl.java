package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.dtos.request.DeviceDetails;
import org.example.ecommercefashion.dtos.request.LoginRequest;
import org.example.ecommercefashion.dtos.request.ResetPasswordRequest;
import org.example.ecommercefashion.dtos.request.UserRequest;
import org.example.ecommercefashion.dtos.response.AuthResponse;
import org.example.ecommercefashion.dtos.response.LoginResponse;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.UserResponse;
import org.example.ecommercefashion.emails.EmailFactory;
import org.example.ecommercefashion.entities.postgres.JwtToken;
import org.example.ecommercefashion.entities.postgres.ResetPasswordToken;
import org.example.ecommercefashion.entities.postgres.User;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.TokenRepository;
import org.example.ecommercefashion.security.JwtUtils;
import org.example.ecommercefashion.services.*;
import org.example.ecommercefashion.utils.HashUtils;
import org.example.ecommercefashion.utils.PasswordUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenService tokenService;

    private final CartService cartService;


    private final ResetPasswordResetTokenService resetPasswordResetTokenService;

    private final UserService userService;

    private final EmailFactory emailFactory;
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResponse resetPassword(String newPassword, String token) {
        ResetPasswordToken resetPasswordToken = resetPasswordResetTokenService.findByToken(token);
        User user = resetPasswordToken.getUser();
        if (PasswordUtils.verifyPassword(newPassword, user.getPassword())) {
            throw new ExceptionHandle(
                    HttpStatus.BAD_REQUEST, ErrorMessage.CURRENT_PASSWORD_SAME_NEW_PASSWORD.val());
        }
        user.setPassword(PasswordUtils.encode(newPassword));
        resetPasswordToken.setIsUsed(true);

        return MessageResponse.builder().message("Password reset successfully! Please login again").build();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (!PasswordUtils.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new ExceptionHandle(HttpStatus.BAD_REQUEST, ErrorMessage.INVALID_PASSWORD.val());
        }

        DeviceDetails deviceDetails = DeviceDetails.fromHeader(request);


        String payloadAccess = jwtUtils.generateToken(user);
        JwtToken accessToken = tokenService.saveTokenToDatabase(
                payloadAccess, user.getId(), TokenType.ACCESS, deviceDetails, null, jwtUtils.getJwtExpiration());


        // tao refresh
        long refreshTokenId = tokenRepository.getNextSeq();
        String payloadRefresh = jwtUtils.generateRefreshToken(user, refreshTokenId);

        JwtToken jwtToken = JwtToken.builder()
                .userId(user.getId())
                .hashToken(HashUtils.getMD5(payloadRefresh))
                .ip(deviceDetails.getIp())
                .browser(deviceDetails.getBrowser())
                .device(deviceDetails.getDevice())
                .expirationAt(new Timestamp(System.currentTimeMillis() + jwtUtils.getJwtRefreshExpiration()))
                .deviceId(deviceDetails.getDeviceId())
                .tokenType(TokenType.REFRESH_TOKEN)
                .referenceToken(accessToken)
                .build();
        JwtToken refreshToken = tokenRepository.save(jwtToken);

        accessToken.setReferenceToken(refreshToken);
        tokenRepository.save(accessToken);


        tokenService.saveTokenToRedis(TokenType.ACCESS, payloadAccess, new HashMap<>(jwtUtils.extractAllClaims(payloadAccess, TokenType.ACCESS)));
        tokenService.saveTokenToRedis(TokenType.REFRESH_TOKEN, payloadRefresh, new HashMap<>(jwtUtils.extractAllClaims(payloadRefresh, TokenType.REFRESH_TOKEN)));
        return LoginResponse.builder()
                .authResponse(
                        AuthResponse.builder()
                                .refreshToken(payloadRefresh)
                                .accessToken(payloadAccess)
                                .build())
                .userResponse(
                        UserResponse.builder()
                                .id(user.getId())
                                .birth(user.getBirth())
                                .email(user.getEmail())
                                .fullName(user.getFullName())
                                .gender(user.getGender())
                                .phoneNumber(user.getPhoneNumber())
                                .avatar(user.getAvatar())
                                .build())
                .build();
    }

    @Override
    @Transactional
    public UserResponse signUp(UserRequest userRequest) {
        User user = userService.createUser(userRequest);
        cartService.create(user);
        return UserResponse.fromEntity(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageResponse requestResetPassword(ResetPasswordRequest request) {
        User user = userService.getUserByEmail(request.getEmail());
        ResetPasswordToken resetPasswordToken = resetPasswordResetTokenService.createToken(user);

        // send Email
        emailFactory.sendEmail(user.getEmail(), "Reset Password", "Reset Password: " + resetPasswordToken.getToken());

        return MessageResponse.builder().message("A password reset link has been sent to your email.").build();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthResponse refreshToken(String refreshToken, HttpServletRequest request) {
        Long userId = Long.parseLong(jwtUtils.extractUserId(refreshToken, TokenType.REFRESH_TOKEN));
        User user = userService.getUserById(userId);

        // tim refresh cu
        JwtToken oldRefreshToken = tokenService.findByHashToken(refreshToken, TokenType.REFRESH_TOKEN).stream()
                .findFirst()
                .orElseThrow(() -> new ExceptionHandle(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED.val()));
        // tạo access mới
        String payloadAccess = jwtUtils.generateToken(user);
        DeviceDetails deviceDetails = DeviceDetails.fromHeader(request);
        JwtToken newAccessToken = tokenService.saveTokenToDatabase(payloadAccess, userId, TokenType.ACCESS, deviceDetails, oldRefreshToken, jwtUtils.getJwtExpiration());

        oldRefreshToken.setReferenceToken(newAccessToken);
        tokenRepository.save(oldRefreshToken);

        // save access moi vao redis
        tokenService.saveTokenToRedis(TokenType.ACCESS, payloadAccess, new HashMap<>(jwtUtils.extractAllClaims(payloadAccess, TokenType.ACCESS)));


        return AuthResponse.builder()
                .accessToken(payloadAccess)
                .refreshToken(refreshToken)
                .build();
    }
}
