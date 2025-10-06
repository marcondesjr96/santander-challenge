package com.santander.challenge.transactions.adapters.dto;

import java.util.UUID;

public record RegisterUserResponse(
        String fullName,
        String cpf,
        String login,
        UUID accountId
) {
}
