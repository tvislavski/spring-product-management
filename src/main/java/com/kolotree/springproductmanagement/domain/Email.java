package com.kolotree.springproductmanagement.domain;

import io.vavr.control.Option;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email newEmailFrom(String email) {
        Option.of(email).filter(s -> !s.trim().isEmpty())
                .getOrElseThrow(() -> new IllegalArgumentException("Email cannot be empty"));
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("Invalid email format");
        return new Email(email);
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !obj.getClass().equals(Email.class)) return false;
        return email.equals(((Email)obj).email);
    }
}