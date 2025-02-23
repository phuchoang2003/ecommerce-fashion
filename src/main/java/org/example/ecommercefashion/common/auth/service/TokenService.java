package org.example.ecommercefashion.common.auth.service;

import org.example.ecommercefashion.common.core.dto.DeviceDetails;
import org.example.ecommercefashion.common.auth.entity.JwtToken;
import org.example.ecommercefashion.common.auth.enums.TokenType;

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
