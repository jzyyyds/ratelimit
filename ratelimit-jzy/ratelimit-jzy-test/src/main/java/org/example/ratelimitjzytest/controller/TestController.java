package org.example.ratelimitjzytest.controller;

import org.example.ratelimitjztcommon.enums.RateLimitTypeEnum;
import org.example.ratelimitjzycore.annotation.RateLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @RateLimit(rateLimitType = RateLimitTypeEnum.COUNTER , key = "test", rate = 10, time = 1)
    public String testLimit() {
        return "hello";
    }
}
