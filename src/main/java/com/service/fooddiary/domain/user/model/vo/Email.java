package com.service.fooddiary.domain.user.model.vo;

import com.service.fooddiary.domain.common.exception.domain.ValidationException;

import java.util.Objects;

public record Email(String email) {
    public Email {
        Objects.requireNonNull(email, "Email must Not be null");

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            String errorEmail = email.isBlank() ? "empty" : email;
            throw new ValidationException("Invalid email format: " + errorEmail);
        }
    }
}
