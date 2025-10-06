package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.adapters.dto.TransactionResponse;
import com.santander.challenge.transactions.adapters.mapper.BalanceMapper;
import com.santander.challenge.transactions.adapters.mapper.TransactionMapper;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.domain.exception.ValidatePositiveAmountException;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
import com.santander.challenge.transactions.infrastructure.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepositMoneyUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final RedisCacheService redisCacheService;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    @Transactional
    public void execute(BigDecimal amount) {
        User authenticatedUser = authenticatedUserProvider.getAuthenticatedUser();

        Account account = accountRepository.findByUserId(authenticatedUser.getId())
                .orElseThrow(AccountNotFoundException::new);

        if (amount == null || amount.signum() <= 0) {
            throw new ValidatePositiveAmountException();
        }

        account.deposit(amount, new BigDecimal("0.0102"));
        accountRepository.update(account);

        Transaction transaction = new Transaction(
                account.getId(),
                TransactionTypeEnum.DEPOSIT,
                amount,
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);

        List<Transaction> transactionList = transactionRepository.findByAccountId(account.getId());
        List<TransactionResponse> transactionResponseList = TransactionMapper.toTransactionResponseList(transactionList);

        BalanceResponse balanceResponse = BalanceMapper.toBalanceResponse(account.getBalance(),transactionResponseList);

        redisCacheService.save(balanceResponse, authenticatedUser.getId());
    }
}
