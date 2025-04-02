package org.example.educheck.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static org.example.educheck.global.security.jwt.JwtTokenUtil.REFRESH_TOKEN_VALIDITY_MILLISECONDS;

@Service
@RequiredArgsConstructor
public class TokenRedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void addTokenToBlackList(String token) {

        redisTemplate.opsForValue().set(token, 1, REFRESH_TOKEN_VALIDITY_MILLISECONDS, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlackListed(String token) {

        return redisTemplate.hasKey(token);
    }
}
