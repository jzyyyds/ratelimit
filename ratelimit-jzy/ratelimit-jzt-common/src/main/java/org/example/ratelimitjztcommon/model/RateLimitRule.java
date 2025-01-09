package org.example.ratelimitjztcommon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateLimitRule {
    private String rateLimitType;
    private Integer rate;
    private String key;
    private Integer time;
}
