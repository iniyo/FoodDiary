package com.service.fooddiary.domain.user.model.vo;

import java.util.Objects;

public record Token(String token) {
    public Token {
        Objects.requireNonNull(token, "name must not be null");
    }
}
