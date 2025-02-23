package org.example.ecommercefashion.common.auth.service;

import org.example.ecommercefashion.common.auth.dto.AuthResponse;

public interface Oauth2Service {

    AuthResponse authenticateFacebookUser(String code);

    AuthResponse authenticateGoogleUser(String code);
}
