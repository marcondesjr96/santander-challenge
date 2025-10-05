package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.domain.exception.InvalidPaymentAmountException;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.model.enums.TransactionTypeEnum;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PayBillUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    public void execute(UUID accountId, BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new InvalidPaymentAmountException();
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);

        account.pay(amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                accountId,
                TransactionTypeEnum.PAYMENT,
                amount,
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);
    }
}
