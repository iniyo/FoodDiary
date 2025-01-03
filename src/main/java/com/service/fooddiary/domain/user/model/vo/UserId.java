package com.service.fooddiary.domain.user.model.vo;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID id) {

    public UserId {
        Objects.requireNonNull(id, "userId must not be null");
    }

    public UserId() {
        this(UUID.randomUUID());
    }
}
