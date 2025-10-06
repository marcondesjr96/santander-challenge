package com.santander.challenge.transactions.application.query;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.domain.exception.BalanceNotFoundException;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetBalanceAndHistoryUseCase {

    private final RedisCacheService redisCacheService;

    public BalanceResponse execute(UUID accountId) {
        return redisCacheService.findByAccountId(accountId).orElseThrow(BalanceNotFoundException::new);
    }
}
