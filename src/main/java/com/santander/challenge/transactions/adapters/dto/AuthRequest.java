package com.santander.challenge.transactions.adapters.dto;

public record AuthRequest(
        String login,
        String password
) {
}
