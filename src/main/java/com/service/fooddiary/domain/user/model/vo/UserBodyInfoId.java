package com.service.fooddiary.domain.user.model.vo;

import java.util.Objects;
import java.util.UUID;

public record UserBodyInfoId(UUID id) {
    public UserBodyInfoId {
        Objects.requireNonNull(id, "id must not be null");
    }
}
