package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.domain.exception.AccountNotFoundException;
import com.santander.challenge.transactions.domain.exception.InvalidPaymentAmountException;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayBillUseCaseTest {

    @Mock private AccountRepository accountRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private RedisCacheService redisCacheService;
    @Mock private AuthenticatedUserProvider authenticatedUserProvider;

    @InjectMocks private PayBillUseCase payBillUseCase;

    private User user;
    private Account account;

    @BeforeEach
    void setup() {
        user = new User(UUID.randomUUID(), "Marcondes Junior", null, "marcondes", "123");
        account = new Account(UUID.randomUUID(), user.getId(), BigDecimal.valueOf(200));
    }

    @Test
    void shouldPayBillSuccessfully() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));
        when(transactionRepository.findByAccountId(account.getId())).thenReturn(List.of());

        payBillUseCase.execute(BigDecimal.valueOf(50));

        verify(accountRepository).update(account);
        verify(transactionRepository).save(any(Transaction.class));
        verify(redisCacheService).save(any(BalanceResponse.class), eq(account.getId()));
    }

    @Test
    void shouldThrowWhenAmountInvalid() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);

        assertThatThrownBy(() -> payBillUseCase.execute(BigDecimal.ZERO))
                .isInstanceOf(InvalidPaymentAmountException.class);
    }

    @Test
    void shouldThrowWhenAccountNotFound() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(user);
        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> payBillUseCase.execute(BigDecimal.valueOf(100)))
                .isInstanceOf(AccountNotFoundException.class);
    }
}
