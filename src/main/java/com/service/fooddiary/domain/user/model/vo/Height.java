package com.service.fooddiary.domain.user.model.vo;

import java.util.Objects;

public record Height(Long height) {
    public Height {
        Objects.requireNonNull(height, "height must not be null");
    }
}
