package com.santander.challenge.transactions.adapters.controller.v1;

import com.santander.challenge.transactions.adapters.dto.BalanceResponse;
import com.santander.challenge.transactions.adapters.dto.DepositRequest;
import com.santander.challenge.transactions.adapters.dto.PayRequest;
import com.santander.challenge.transactions.application.command.DepositMoneyUseCase;
import com.santander.challenge.transactions.application.command.PayBillUseCase;
import com.santander.challenge.transactions.application.query.GetBalanceAndHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final DepositMoneyUseCase depositMoneyUseCase;
    private final PayBillUseCase payBillUseCase;
    private final GetBalanceAndHistoryUseCase getBalanceAndHistoryUseCase;

    @PostMapping("/{accountId}/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void deposit(@PathVariable UUID accountId, @RequestBody DepositRequest request) {
        depositMoneyUseCase.execute(accountId, request.amount());
    }

    @PostMapping("/{accountId}/pay")
    @ResponseStatus(HttpStatus.OK)
    public void pay(@PathVariable UUID accountId, @RequestBody PayRequest request) {
        payBillUseCase.execute(accountId, request.amount());
    }

    @GetMapping("/{accountId}/balance")
    @ResponseStatus(HttpStatus.OK)
    public BalanceResponse getBalance(@PathVariable UUID accountId) {
        return getBalanceAndHistoryUseCase.execute(accountId);
    }
}
