package org.example.educheck.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;

    public void addTokenBlackList(String token, long validityMilliSeconds) {

        String key = "token:" + token;
        valueOps.set(key, token, validityMilliSeconds, TimeUnit.MILLISECONDS);
    }
}
