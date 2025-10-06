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

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public void deposit(@RequestBody DepositRequest request) {
        depositMoneyUseCase.execute( request.amount());
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    public void pay(@RequestBody PayRequest request) {
        payBillUseCase.execute(request.amount());
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public BalanceResponse getBalance() {
        return getBalanceAndHistoryUseCase.execute();
    }
}
