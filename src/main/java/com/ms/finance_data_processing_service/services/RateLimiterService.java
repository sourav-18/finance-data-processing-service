package com.ms.finance_data_processing_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    @Value("${rate-limit.refill.time-in-second}")
    private int globalRateLimitTimeInSecond=60;

    @Value("${rate-limit.bucket.count}")
    private int bucketCount;
    private final RedisTemplate<String, String> redisTemplate;

    public boolean isUserHaveToken(String userId){
//        System.out.println(bucketCount);
        String key="global:rate-limit:"+userId;
        Long current=redisTemplate.opsForValue().increment(key);
        if(current==1){
            redisTemplate.expire(key, globalRateLimitTimeInSecond,TimeUnit.SECONDS);
        }
        return current <= bucketCount;
    }

}
