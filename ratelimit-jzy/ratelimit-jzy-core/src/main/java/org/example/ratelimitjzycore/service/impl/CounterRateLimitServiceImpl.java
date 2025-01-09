package org.example.ratelimitjzycore.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.ratelimitjztcommon.model.RateLimitRule;
import org.example.ratelimitjzycore.service.RateLimitService;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CounterRateLimitServiceImpl implements RateLimitService {
    private final RScript rScript;

    public CounterRateLimitServiceImpl(RedissonClient redissonClient){
        this.rScript = redissonClient.getScript(LongCodec.INSTANCE);
    }

    @Override
    public boolean isAllow(RateLimitRule rateLimitRule) {
        String script = getScript();
        List<Object> keys = Collections.singletonList(rateLimitRule.getKey());
        Long result = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER, keys, rateLimitRule.getRate(), rateLimitRule.getTime());
        if (Objects.isNull(result)||result.intValue()>rateLimitRule.getRate()) {
            return false;
        }
        return true;
    }

    @Override
    public String getScript() {
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("META-INF/count.lua");
        String luaScript = "";
        try {
            luaScript = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return luaScript;
    }
}
