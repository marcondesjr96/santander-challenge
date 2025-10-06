package com.santander.challenge.transactions.application.query;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.domain.exception.BalanceNotFoundException;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
import com.santander.challenge.transactions.infrastructure.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetBalanceAndHistoryUseCase {

    private final RedisCacheService redisCacheService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public BalanceResponse execute() {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();
        return redisCacheService.findByUserId(authenticatedUser.getId()).orElseThrow(BalanceNotFoundException::new);
    }
}
