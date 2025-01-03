package com.service.fooddiary.domain.user.model.vo;

import java.util.Objects;

public record UserName(String name) {
    public UserName {
        Objects.requireNonNull(name, "name must not be null");
    }
}
