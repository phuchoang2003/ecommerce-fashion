package org.example.ecommercefashion.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.ecommercefashion.enums.TokenType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AspectAuthorization {

    private final HttpServletRequest request;

    private final JwtUtils jwtUtils;


    @Around("@annotation(org.example.ecommercefashion.security.Protected)")
    public Object authorization(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Protected annotation = method.getAnnotation(Protected.class);
        TokenType tokenType = annotation.value();
        String token = request.getHeader("Authorization");
        jwtUtils.isTokenValid(token, tokenType);

        return joinPoint.proceed();
    }
}
