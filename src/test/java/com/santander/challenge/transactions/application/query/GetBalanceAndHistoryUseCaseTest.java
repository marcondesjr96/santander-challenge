package com.santander.challenge.transactions.application.query;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.domain.exception.BalanceNotFoundException;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
import com.santander.challenge.transactions.infrastructure.security.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBalanceAndHistoryUseCaseTest {

    @Mock
    private RedisCacheService redisCacheService;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    @InjectMocks
    private GetBalanceAndHistoryUseCase getBalanceAndHistoryUseCase;

    private User authenticatedUser;
    private BalanceResponse cachedResponse;

    @BeforeEach
    void setup() {
        authenticatedUser = new User("Marcondes Junior", null, "marcondes", "hashed");
        cachedResponse = new BalanceResponse(BigDecimal.valueOf(500.00), List.of());
    }

    @Test
    void shouldReturnBalanceFromCacheWhenFound() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(redisCacheService.findByUserId(authenticatedUser.getId())).thenReturn(Optional.of(cachedResponse));

        BalanceResponse response = getBalanceAndHistoryUseCase.execute();

        assertThat(response).isNotNull();
        assertThat(response.totalBalance()).isEqualByComparingTo("500.00");
        verify(redisCacheService).findByUserId(authenticatedUser.getId());
    }

    @Test
    void shouldThrowExceptionWhenBalanceNotFound() {
        when(authenticatedUserProvider.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(redisCacheService.findByUserId(authenticatedUser.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getBalanceAndHistoryUseCase.execute())
                .isInstanceOf(BalanceNotFoundException.class);

        verify(redisCacheService).findByUserId(authenticatedUser.getId());
    }
}
