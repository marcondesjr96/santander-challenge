package com.santander.challenge.transactions.adapters.mapper;

import com.santander.challenge.transactions.adapters.dto.RegisterUserResponse;
import com.santander.challenge.transactions.domain.model.User;

public class RegisterMapper {

    public static RegisterUserResponse toResponse (User user){
        return new RegisterUserResponse(user.getFullName(), user.getCpf().getValue(), user.getLogin());
    }
}
