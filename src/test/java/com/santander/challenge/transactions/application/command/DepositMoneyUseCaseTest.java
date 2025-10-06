package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.adapters.dto.TransactionResponse;
import com.santander.challenge.transactions.adapters.mapper.BalanceMapper;
import com.santander.challenge.transactions.adapters.mapper.TransactionMapper;
import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.domain.exception.ValidatePositiveAmountException;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
import com.santander.challenge.transactions.infrastructure.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositMoneyUseCaseTest {

    @Mock private AccountRepository accountRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private RedisCacheService redisCacheService;
    @Mock private AuthenticatedUserProvider authenticatedUserProvider;

    @InjectMocks private DepositMoneyUseCase depositMoneyUseCase;

    private User user;
    private Account account;

    @BeforeEach
    void setup() {
        user = new User(UUID.randomUUID(), "Marcondes Junior", null, "marcondes", "123");
        account = new Account(UUID.randomUUID(), user.getId(), BigDecimal.valueOf(100));
    }

    @Test
    void shouldDepositSuccessfully() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));
        when(transactionRepository.findByAccountId(account.getId())).thenReturn(List.of());

        depositMoneyUseCase.execute(BigDecimal.valueOf(50));

        verify(accountRepository).update(account);
        verify(transactionRepository).save(any(Transaction.class));
        verify(redisCacheService).save(any(BalanceResponse.class), eq(user.getId()));
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> depositMoneyUseCase.execute(BigDecimal.valueOf(50)))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void shouldThrowWhenAmountInvalid() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> depositMoneyUseCase.execute(BigDecimal.ZERO))
                .isInstanceOf(ValidatePositiveAmountException.class);
    }
}
