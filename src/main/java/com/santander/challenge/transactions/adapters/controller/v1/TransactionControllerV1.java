package com.santander.challenge.transactions.adapters.controller.v1;

import com.santander.challenge.transactions.adapters.dto.TransactionResponse;
import com.santander.challenge.transactions.adapters.mapper.TransactionMapper;
import com.santander.challenge.transactions.domain.model.Transaction;
import com.santander.challenge.transactions.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionControllerV1 {

    private final TransactionRepository transactionRepository;

    @GetMapping("/account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> listByAccount(@PathVariable UUID accountId) {
        List<Transaction> transactionList = transactionRepository.findByAccountId(accountId);
        return transactionList.stream().map(TransactionMapper::toTransactionRepsponse).toList();
    }
}
