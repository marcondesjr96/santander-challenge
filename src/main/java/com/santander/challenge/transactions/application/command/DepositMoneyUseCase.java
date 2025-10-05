package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.domain.exception.ValidatePositiveAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepositMoneyUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    public void execute(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        if (amount == null || amount.signum() <= 0) {
            throw new ValidatePositiveAmountException();
        }

        account.deposit(amount, new BigDecimal("0.0102"));
        accountRepository.update(account);

        Transaction transaction = new Transaction(
                accountId,
                TransactionTypeEnum.DEPOSIT,
                amount,
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);
    }
}
