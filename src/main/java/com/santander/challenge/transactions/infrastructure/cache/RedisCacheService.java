package com.santander.challenge.transactions.infrastructure.cache;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class RedisCacheService {

    private final RedisTemplate<String, BalanceResponse> redisTemplate;

    private String buildKey(UUID userId) {
        return "user:" + userId;
    }

    public void save(BalanceResponse balanceResponse, UUID userId) {
        redisTemplate.opsForValue().set(buildKey(userId), balanceResponse);
    }

    public Optional<BalanceResponse> findByUserId(UUID userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(buildKey(userId)));
    }
}
