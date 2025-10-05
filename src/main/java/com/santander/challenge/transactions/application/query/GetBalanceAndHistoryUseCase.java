package com.santander.challenge.transactions.application.query;

import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Map;

@Service
public class GetBalanceAndHistoryUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public GetBalanceAndHistoryUseCase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Map<String, Object> execute(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        var history = transactionRepository.findByAccountId(accountId);

        return Map.of(
                "totalBalance", account.getBalance(),
                "transactions", history
        );
    }
}
