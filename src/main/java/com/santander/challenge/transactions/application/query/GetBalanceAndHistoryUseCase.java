package com.santander.challenge.transactions.application.query;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.adapters.dto.TransactionResponse;
import com.santander.challenge.transactions.adapters.mapper.BalanceMapper;
import com.santander.challenge.transactions.adapters.mapper.TransactionMapper;
import com.santander.challenge.transactions.domain.exception.LoginAlreadyExistsException;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GetBalanceAndHistoryUseCase {

    private final RedisCacheService redisCacheService;

    public BalanceResponse execute(UUID accountId) {
        return redisCacheService.findByAccountId(accountId).orElseThrow(LoginAlreadyExistsException::new);
    }
}
