package org.example.ecommercefashion.common.auth.annotation.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.ecommercefashion.common.auth.annotation.RequestHeaderIdUser;
import org.example.ecommercefashion.common.auth.jwt.JwtUtils;
import org.example.ecommercefashion.common.auth.enums.TokenType;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class RequestHeaderIdUserValidator implements HandlerMethodArgumentResolver {

    private final JwtUtils jwtUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestHeaderIdUser.class) != null;
    }

    @Override
    public Long resolveArgument(@NonNull MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        String token = nativeWebRequest.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return Long.parseLong(jwtUtils.extractUserId(token, TokenType.ACCESS));
        }
        return null;
    }
}
