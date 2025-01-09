package org.example.ratelimitjzystarter.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.ratelimitjztcommon.model.RateLimitRule;
import org.example.ratelimitjzycore.annotation.RateLimit;
import org.example.ratelimitjzycore.handler.RateLimitHandler;
import org.example.ratelimitjzycore.provide.RuleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class RateLimiterInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterInterceptor.class);

    private final RateLimitHandler rateLimitHandler;
    private final RuleProvider ruleProvider;

    public RateLimiterInterceptor(RateLimitHandler rateLimitHandler,RuleProvider ruleProvider){
        this.rateLimitHandler = rateLimitHandler;
        this.ruleProvider = ruleProvider;
    }

    @Around(value = "@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        //拼接出Rule的数据结构
        RateLimitRule rule = ruleProvider.getRule(joinPoint, rateLimit);
        boolean allow = rateLimitHandler.isAllow(rule);
        if (!allow) {
            //说明被限流了
            logger.info("被限流了！！！");
            throw new RuntimeException("限流");
        }
        logger.info("成功");
        return joinPoint.proceed();
    }

}
