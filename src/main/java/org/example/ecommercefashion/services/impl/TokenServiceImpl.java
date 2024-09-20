package org.example.ecommercefashion.services.impl;

import com.longnh.exceptions.ExceptionHandle;
import com.longnh.utils.HashUtil;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.dtos.request.DeviceDetails;
import org.example.ecommercefashion.entities.postgres.JwtToken;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.TokenRepository;
import org.example.ecommercefashion.security.JwtUtils;
import org.example.ecommercefashion.services.TokenService;
import org.example.ecommercefashion.utils.HashUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    private final JwtUtils jwtUtils;

    private final RedisTemplate<String, Map<String, Object>> redisTemplateMap;

    @Override
    public List<JwtToken> findByHashToken(String hashToken, TokenType type) {
        List<JwtToken> tokens = tokenRepository.findByHashToken(HashUtils.getMD5(jwtUtils.removeBear(hashToken)), type);
        if (tokens.isEmpty())
            throw new ExceptionHandle(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED.val());
        return tokens;
    }

    @Override
    public void saveTokenToRedis(TokenType type, String payload, Map<String, Object> claims) {
        long exp = jwtUtils.extractExpiration(payload, type).getTime();
        redisTemplateMap
                .opsForValue()
                .set(jwtUtils.getTokenKey(type, payload), claims, Duration.ofMillis(exp - System.currentTimeMillis()));
    }

    @Override
    public JwtToken saveTokenToDatabase(String payload,
                                        Long userId,
                                        TokenType tokenType,
                                        DeviceDetails deviceDetails,
                                        JwtToken referenceToken,
                                        Long expiredAt) {
        String hashPayLoad = HashUtil.getMD5(payload);

        JwtToken jwtToken = JwtToken.builder()
                .userId(userId)
                .hashToken(hashPayLoad)
                .ip(deviceDetails.getIp())
                .browser(deviceDetails.getBrowser())
                .device(deviceDetails.getDevice())
                .expirationAt(new Timestamp(System.currentTimeMillis() + expiredAt))
                .deviceId(deviceDetails.getDeviceId())
                .tokenType(tokenType)
                .referenceToken(referenceToken)
                .build();

        return tokenRepository.save(jwtToken);

    }
}
