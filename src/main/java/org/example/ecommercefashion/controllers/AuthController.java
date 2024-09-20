package org.example.ecommercefashion.controllers;

import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.*;
import org.example.ecommercefashion.dtos.response.AuthResponse;
import org.example.ecommercefashion.dtos.response.LoginResponse;
import org.example.ecommercefashion.dtos.response.MessageResponse;
import org.example.ecommercefashion.dtos.response.UserResponse;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.security.Protected;
import org.example.ecommercefashion.services.AuthenticationService;
import org.example.ecommercefashion.services.Oauth2Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    private final Oauth2Service oauth2Service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return ResponseEntity.ok(authenticationService.login(loginRequest, request));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authenticationService.signUp(userRequest));
    }


    @PostMapping("/request-reset-password")
    public ResponseEntity<MessageResponse> requestResetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.requestResetPassword(request));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestParam("token") String token, @RequestBody @Valid ForgotPasswordRequest request) {
        return ResponseEntity.ok(authenticationService.resetPassword(request.getNewPassword(), token));
    }


    @PostMapping("/refresh-token")
    @Protected(TokenType.REFRESH_TOKEN)
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestHeader("Authorization") String refreshToken,
            HttpServletRequest request) {
        AuthResponse res = authenticationService.refreshToken(refreshToken, request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/facebook-login")
    public AuthResponse facebookLogin(@RequestBody FacebookLoginRequest facebookLoginRequest) {
        return oauth2Service.authenticateFacebookUser(facebookLoginRequest.getCode());
    }

    @PostMapping("/google-login")
    public AuthResponse googleLogin(@RequestParam("code") String code) {
        return oauth2Service.authenticateGoogleUser(code);
    }
}
