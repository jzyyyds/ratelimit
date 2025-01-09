package org.example.ratelimitjztcommon.enums;

public enum RateLimitTypeEnum {
    //计数器算法
    COUNTER("count"),
    //滑动窗口
    TIME_WINDOW("time_window"),
    //漏桶
    LEAKY_BUCKET("leaky_bucket"),
    //令牌桶
    TOKEN_BUCKET("token_bucket"),;

    private String key;

    RateLimitTypeEnum(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }

}
