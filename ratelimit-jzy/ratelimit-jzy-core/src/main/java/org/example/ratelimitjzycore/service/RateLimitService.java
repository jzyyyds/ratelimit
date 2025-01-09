package org.example.ratelimitjzycore.service;

import org.example.ratelimitjztcommon.model.RateLimitRule;

public interface RateLimitService<T> {
    boolean isAllow(RateLimitRule rateLimitRule);
    String getScript();
}
