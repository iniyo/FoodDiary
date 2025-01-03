package com.service.fooddiary.domain.user.model.vo;

import java.util.Objects;

public record PhoneNumber(String phoneNumber) {
    public PhoneNumber {
        Objects.requireNonNull(phoneNumber, "phoneNumber must not be null");
    }
}
