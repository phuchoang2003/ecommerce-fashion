package org.example.ecommercefashion.common.auth.port;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.auth.annotation.Protected;
import org.example.ecommercefashion.common.auth.dto.FacebookLoginRequest;
import org.example.ecommercefashion.common.auth.service.Oauth2Service;
import org.example.ecommercefashion.common.auth.dto.ForgotPasswordRequest;
import org.example.ecommercefashion.common.auth.dto.LoginRequest;
import org.example.ecommercefashion.common.auth.dto.ResetPasswordRequest;
import org.example.ecommercefashion.module.user.dto.UserRequest;
import org.example.ecommercefashion.common.auth.dto.AuthResponse;
import org.example.ecommercefashion.common.auth.dto.LoginResponse;
import org.example.ecommercefashion.module.chat.dto.MessageResponse;
import org.example.ecommercefashion.module.user.dto.UserResponse;
import org.example.ecommercefashion.common.auth.enums.TokenType;
import org.example.ecommercefashion.common.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
