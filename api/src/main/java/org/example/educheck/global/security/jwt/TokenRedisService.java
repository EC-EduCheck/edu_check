package org.example.educheck.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static org.example.educheck.global.security.jwt.JwtTokenUtil.REFRESH_TOKEN_VALIDITY_MILLISECONDS;

@Service
@RequiredArgsConstructor
public class TokenRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOps;

    public void addTokenToBlackList(String token) {

        valueOps.set(token, 1, REFRESH_TOKEN_VALIDITY_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlackListed(String token) {

        return redisTemplate.hasKey(token);
    }
}
