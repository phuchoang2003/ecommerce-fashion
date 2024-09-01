package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.response.AuthResponse;
import org.example.ecommercefashion.entities.mysql.RefreshToken;
import org.example.ecommercefashion.entities.mysql.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RefreshTokenService {

    void saveUserToken(User user, String token);

    RefreshToken findRefreshToken(String token);

    void revokeAllUserToken(User user);

    AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException;
}
