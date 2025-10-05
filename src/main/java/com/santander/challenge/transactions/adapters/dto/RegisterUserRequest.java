package com.santander.challenge.transactions.adapters.dto;

public record RegisterUserRequest(
        String fullName,
        String cpf,
        String login,
        String password
) {
}