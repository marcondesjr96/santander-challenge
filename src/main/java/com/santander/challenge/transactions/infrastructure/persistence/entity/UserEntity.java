package com.santander.challenge.transactions.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "users",
       indexes = {@Index(name = "uk_users_cpf", columnList = "cpf", unique = true),
                  @Index(name = "uk_users_login", columnList = "login", unique = true)})
public class UserEntity extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 80, unique = true)
    private String login;

    @Column(nullable = false, length = 255)
    private String passwordHash;
}
