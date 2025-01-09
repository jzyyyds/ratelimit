package org.example.ratelimitjzycore.annotation;

import org.example.ratelimitjztcommon.enums.RateLimitTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流的自定义注解
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RateLimit {
    RateLimitTypeEnum rateLimitType() default RateLimitTypeEnum.COUNTER;
    int rate();
    String key();
    int time();
}
