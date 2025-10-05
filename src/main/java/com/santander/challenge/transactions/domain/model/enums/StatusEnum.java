package com.santander.challenge.transactions.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
@AllArgsConstructor
@Getter
public enum StatusEnum {

    ACTIVE(1L, "Active"),
    DISABLED(2L, "Disabled");

    private final Long id;
    private final String status;

    public static StatusEnum getById(Long id) {
        return Arrays.stream(StatusEnum.values())
                .filter(value -> value.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static StatusEnum getByType(String type) {
        return Arrays.stream(StatusEnum.values())
                .filter(value -> value.getStatus().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }

}
