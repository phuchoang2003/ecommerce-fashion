package org.example.ecommercefashion.common.auth.annotation;


import org.example.ecommercefashion.common.auth.enums.TokenType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Protected {
    TokenType value();
}
