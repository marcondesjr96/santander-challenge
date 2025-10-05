package com.santander.challenge.transactions.application.command;

import com.santander.challenge.transactions.adapters.dto.RegisterUserResponse;
import com.santander.challenge.transactions.adapters.mapper.RegisterMapper;
import com.santander.challenge.transactions.domain.exception.CPFAlreadyRegisteredException;
import com.santander.challenge.transactions.domain.exception.InvalidCpfException;
import com.santander.challenge.transactions.domain.exception.LoginAlreadyExistsException;
import com.santander.challenge.transactions.domain.model.Cpf;
import com.santander.challenge.transactions.domain.model.User;
import com.santander.challenge.transactions.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;


    public RegisterUserResponse execute(String fullName, String rawCpf, String login, String rawPassword) {
        try {
            Cpf cpf = new Cpf(rawCpf);

            userRepository.findByLogin(login)
                .ifPresent(u -> { throw new LoginAlreadyExistsException(); });

            userRepository.findByCpf(cpf.getValue())
                .ifPresent(u -> { throw new CPFAlreadyRegisteredException(); });

//            String hash = passwordEncoder.encode(rawPassword);
            User user = new User(fullName, cpf, login, rawPassword);

            User userSaved = userRepository.save(user);
            return RegisterMapper.toResponse(userSaved);

        } catch (IllegalArgumentException e) {
            throw new InvalidCpfException();
        }
    }
}
