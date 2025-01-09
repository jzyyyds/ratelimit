package org.example.ratelimitjzycore.handler;

import lombok.extern.slf4j.Slf4j;

import org.example.ratelimitjztcommon.enums.RateLimitTypeEnum;
import org.example.ratelimitjztcommon.model.RateLimitRule;
import org.example.ratelimitjzycore.service.RateLimitService;
import org.example.ratelimitjzycore.service.impl.CounterRateLimitServiceImpl;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RateLimitHandler {
    private final Logger logger = LoggerFactory.getLogger(RateLimitHandler.class);
    private static Map<String, RateLimitService> rateLimitServiceMap = new ConcurrentHashMap<String,RateLimitService>();

    public RateLimitHandler(RedissonClient redissonClient){
        rateLimitServiceMap.put(RateLimitTypeEnum.COUNTER.getKey(),new CounterRateLimitServiceImpl(redissonClient));
    }

    public boolean isAllow(RateLimitRule rateLimitRule){
        RateLimitService rateLimitService = rateLimitServiceMap.get(rateLimitRule.getRateLimitType());
        return rateLimitService.isAllow(rateLimitRule);
    }
}
