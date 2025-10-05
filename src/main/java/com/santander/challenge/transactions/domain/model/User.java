package com.santander.challenge.transactions.domain.model;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String fullName;
    private final Cpf cpf;
    private final String login;
    private final String passwordHash;


    public User(UUID id, String fullName, Cpf cpf, String login, String passwordHash) {
        this.id = id;
        this.fullName = fullName;
        this.cpf = cpf;
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
