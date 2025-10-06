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

    private String buildKey(UUID accountId) {
        return "account:" + accountId;
    }

    public void save(BalanceResponse balanceResponse, UUID accountId) {
        redisTemplate.opsForValue().set(buildKey(accountId), balanceResponse);
    }

    public Optional<BalanceResponse> findByAccountId(UUID accountId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(buildKey(accountId)));
    }
}
