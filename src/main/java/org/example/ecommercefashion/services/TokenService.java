package org.example.ecommercefashion.services;

import org.example.ecommercefashion.dtos.request.DeviceDetails;
import org.example.ecommercefashion.entities.postgres.JwtToken;
import org.example.ecommercefashion.enums.TokenType;

import java.util.List;
import java.util.Map;

public interface TokenService {
    JwtToken saveTokenToDatabase(String payload,
                                 Long userId,
                                 TokenType tokenType,
                                 DeviceDetails deviceDetails,
                                 JwtToken referenceToken,
                                 Long expiredAt);

    void saveTokenToRedis(TokenType type, String payload, Map<String, Object> claims);


    List<JwtToken> findByHashToken(String hashToken, TokenType type);
}
