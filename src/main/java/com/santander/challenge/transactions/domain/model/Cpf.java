package com.santander.challenge.transactions.domain.model;

import com.santander.challenge.transactions.domain.exception.InvalidCpfException;

import java.util.Objects;

public final class Cpf {
    private final String value;

    public Cpf(String raw) {
        String digits = raw == null ? "" : raw.replaceAll("\\D", "");
        if (!isValid(digits)) throw new InvalidCpfException();
        this.value = digits;
    }

    private static boolean isValid(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.chars().distinct().count() == 1) return false;
        try {
            int d1 = 0, d2 = 0;
            for (int i = 0; i < 9; i++) {
                int n = cpf.charAt(i) - '0';
                d1 += n * (10 - i);
                d2 += n * (11 - i);
            }
            d1 = 11 - (d1 % 11);
            d1 = (d1 > 9) ? 0 : d1;
            d2 += d1 * 2;
            d2 = 11 - (d2 % 11);
            d2 = (d2 > 9) ? 0 : d2;
            return d1 == (cpf.charAt(9) - '0') && d2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return Objects.equals(value, cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "Cpf{" +
                "value='" + value + '\'' +
                '}';
    }
}
