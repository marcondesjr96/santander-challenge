package com.santander.challenge.transactions.domain.model.enums;

import java.util.Arrays;

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

    StatusEnum(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
