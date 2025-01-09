package org.example.ratelimitjzycore.provide;

import com.sun.javafx.css.Rule;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.example.ratelimitjztcommon.enums.RateLimitTypeEnum;
import org.example.ratelimitjztcommon.model.RateLimitRule;
import org.example.ratelimitjzycore.annotation.RateLimit;

public class RuleProvider {
    public RateLimitRule getRule(ProceedingJoinPoint joinPoint, RateLimit rateLimit){
        String key = rateLimit.key();
        int rate = rateLimit.rate();
        RateLimitTypeEnum rateLimitTypeEnum = rateLimit.rateLimitType();
        RateLimitRule rateLimitRule = RateLimitRule.builder()
                .rate(rate)
                .key(key)
                .rateLimitType(rateLimitTypeEnum.getKey())
                .time(rateLimit.time())
                .build();
        return rateLimitRule;
    }
}
