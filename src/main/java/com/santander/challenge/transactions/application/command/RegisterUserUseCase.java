package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.RegisterUserResponse;
import com.santander.challenge.transactions.adapters.mapper.RegisterMapper;
import com.santander.challenge.transactions.domain.exception.CPFAlreadyRegisteredException;
import com.santander.challenge.transactions.domain.exception.InvalidCpfException;
import com.santander.challenge.transactions.domain.exception.LoginAlreadyExistsException;
import com.santander.challenge.transactions.domain.model.Account;
import com.santander.challenge.transactions.domain.model.Cpf;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.AccountRepository;
import com.santander.challenge.transactions.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public RegisterUserResponse execute(String fullName, String rawCpf, String login, String rawPassword) {
        try {
            Cpf cpf = new Cpf(rawCpf);

            userRepository.findByLogin(login)
                .ifPresent(u -> { throw new LoginAlreadyExistsException(); });

            userRepository.findByCpf(cpf.getValue())
                .ifPresent(u -> { throw new CPFAlreadyRegisteredException(); });

            String hash = passwordEncoder.encode(rawPassword);
            User user = new User(fullName, cpf, login, hash);

            User userSaved = userRepository.save(user);

            Account account = new Account(userSaved.getId(), BigDecimal.ZERO);
            accountRepository.save(account);
            return RegisterMapper.toResponse(userSaved);

        } catch (IllegalArgumentException e) {
            throw new InvalidCpfException();
        }
    }
}
