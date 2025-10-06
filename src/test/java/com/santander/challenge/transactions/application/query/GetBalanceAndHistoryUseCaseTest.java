package com.santander.challenge.transactions.application.query;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.domain.exception.BalanceNotFoundException;
import com.santander.challenge.transactions.infrastructure.cache.RedisCacheService;
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

    @InjectMocks
    private GetBalanceAndHistoryUseCase getBalanceAndHistoryUseCase;

    private UUID accountId;
    private BalanceResponse cachedResponse;

    @BeforeEach
    void setup() {
        accountId = UUID.randomUUID();
        cachedResponse = new BalanceResponse(BigDecimal.valueOf(500.00), List.of());
    }

    @Test
    void shouldReturnBalanceFromCacheWhenFound() {
        when(redisCacheService.findByAccountId(accountId)).thenReturn(Optional.of(cachedResponse));

        BalanceResponse response = getBalanceAndHistoryUseCase.execute(accountId);

        assertThat(response).isNotNull();
        assertThat(response.totalBalance()).isEqualByComparingTo("500.00");
        verify(redisCacheService).findByAccountId(accountId);
    }

    @Test
    void shouldThrowExceptionWhenBalanceNotFound() {
        when(redisCacheService.findByAccountId(accountId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getBalanceAndHistoryUseCase.execute(accountId))
                .isInstanceOf(BalanceNotFoundException.class);

        verify(redisCacheService).findByAccountId(accountId);
    }
}
