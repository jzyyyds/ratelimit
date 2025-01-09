package org.example.ratelimitjzystarter.config;

import org.example.ratelimitjzycore.handler.RateLimitHandler;
import org.example.ratelimitjzycore.provide.RuleProvider;
import org.example.ratelimitjzystarter.aop.RateLimiterInterceptor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Indexed;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@Import(RateLimiterInterceptor.class)
public class RateLimitConfig {
    private final Logger logger = LoggerFactory.getLogger(RateLimitConfig.class);
    @Bean(name = "rateLimitRedission")
    public RedissonClient redissonClient(RedisProperties properties) {
        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE);

        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword())
                .setConnectionPoolSize(properties.getPoolSize())
                .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                .setIdleConnectionTimeout(properties.getIdleTimeout())
                .setConnectTimeout(properties.getConnectTimeout())
                .setRetryAttempts(properties.getRetryAttempts())
                .setRetryInterval(properties.getRetryInterval())
                .setPingConnectionInterval(properties.getPingInterval())
                .setKeepAlive(properties.isKeepAlive())
        ;

        RedissonClient redissonClient = Redisson.create(config);

        logger.info("限流组件redis配置初始话成功。{} {} {}", properties.getHost(), properties.getPoolSize(), !redissonClient.isShutdown());

        return redissonClient;
    }

    @Bean(name = "rateLimitHandle")
    public RateLimitHandler getRateLimitHandler(RedissonClient rateLimitRedission){
        logger.info("开始初始化处理器");
        return new RateLimitHandler(rateLimitRedission);
    }

    @Bean
    public RuleProvider getRuleProvider() {
        return new RuleProvider();
    }

}
