package com.santander.challenge.transactions.adapters.dto;

public record RegisterUserResponse(
        String fullName,
        String cpf,
        String login
) {
}
