package org.example.ecommercefashion.security;

import com.longnh.exceptions.ExceptionHandle;
import com.longnh.utils.HashUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ecommercefashion.entities.postgres.User;
import org.example.ecommercefashion.enums.TokenType;
import org.example.ecommercefashion.exceptions.ErrorMessage;
import org.example.ecommercefashion.repositories.postgres.PermissionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
@Getter
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class JwtUtils {

    private final PermissionRepository permissionRepository;

    private final RedisTemplate<String, Map<String, Object>> redisTemplateMap;


    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${refresh-token.key}")
    private String jwtRefreshKey;
    @Value(("${refresh-token.expiration}"))
    private long jwtRefreshExpiration;


    public String removeBearer(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    private String getKeyByType(TokenType type) {
        return type == TokenType.ACCESS ? jwtKey : jwtRefreshKey;
    }

    public String extractUserId(String token, TokenType type) {
        return extractClaim(token, claims -> claims.get(ClaimKey.USER_ID.val).toString(), type);
    }

    public Claims extractAllClaims(String token, TokenType type) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey(getKeyByType(type))).build().parseClaimsJws(removeBear(token)).getBody();
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, TokenType type) {
        final Claims claims = extractAllClaims(token, type);
        return claimsResolver.apply(claims);
    }

    private Key getSigningKey(String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token, TokenType type) {
        return extractExpiration(token, type).before(new Date());
    }

    public Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, Claims::getExpiration, type);
    }

    private Set<String> getPermissionsUserByRBAC(User user) {
        return permissionRepository.findAllPermissionsUserByRBAC(user.getId());
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public String generateRefreshToken(User user, long refreshTokenId) {
        return buildRefreshToken(user, refreshTokenId, jwtRefreshExpiration, jwtRefreshKey);
    }

    private String buildRefreshToken(User user, long refreshTokenId, long expiration, String key) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ClaimKey.USER_ID.val, user.getId());
        claims.put(ClaimKey.REFRESH_TOKEN_ID.val, refreshTokenId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private String buildAccessToken(
            Map<String, Object> extraClaims, User user, long expiration, String key) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put(ClaimKey.USER_ID.val, user.getId());
        claims.put(ClaimKey.PERMISSION.val, getPermissionsUserByRBAC(user));
        claims.put(ClaimKey.IS_ADMIN.val, user.getIsAdmin());
        claims.put(ClaimKey.AVATAR.val, user.getAvatar());
        claims.put(ClaimKey.PHONE_NUMBER.val, user.getPhoneNumber());
        claims.put(ClaimKey.EMAIL.val, user.getEmail());
        claims.put(ClaimKey.FULL_NAME.val, user.getFullName());
        claims.put(ClaimKey.REX.val, System.currentTimeMillis() + jwtRefreshExpiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateToken(Map<String, Object> extraClaims, User user) {
        return buildAccessToken(extraClaims, user, jwtExpiration, jwtKey);
    }


    public void isTokenValid(String token, TokenType type) {
        Map<String, Object> tokenInRedis = getTokenClaimInRedis(type, token);
        if (tokenInRedis == null) {
            throw new ExceptionHandle(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED.val());
        }
    }

    private Map<String, Object> getTokenClaimInRedis(TokenType type, String token) {
        return redisTemplateMap.opsForValue().get(getTokenKey(type, token));
    }

    public String getTokenKey(TokenType type, String token) {
        if (!validateJwtToken(token, type)) {
            throw new ExceptionHandle(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED.val());
        }
        Claims claims = extractAllClaims(token, type);

        return String.format(
                "auth:token:%s:user:%s:hash:%s",
                type,
                claims.get("user-id"),
                HashUtil.getMD5(removeBear(token))
        );
    }

    private boolean validateJwtToken(String authToken, TokenType type) {
        if (authToken == null) {
            return false;
        }
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(getKeyByType(type))).build().parseClaimsJws(removeBear(authToken));
            return true;
        } catch (SecurityException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String removeBear(String bearToken) {
        if (StringUtils.hasText(bearToken) && bearToken.startsWith("Bearer ")) {
            return bearToken.substring(7);
        }
        return bearToken;
    }


    public enum ClaimKey {
        FULL_NAME("full-name"),
        USER_ID("user-id"),
        EMAIL("email"),
        PHONE_NUMBER("phone-number"),
        AVATAR("avatar"),
        IS_ADMIN("is-admin"),
        REX("rex"),
        PERMISSION("permissions"),
        REFRESH_TOKEN_ID("refresh-token-id");


        public final String val;

        ClaimKey(String label) {
            val = label;
        }
    }
}
