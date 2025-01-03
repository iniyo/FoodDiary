package com.service.fooddiary.domain.common.exception.domain;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;

public class PayloadSerializationException extends RuntimeException {
    public PayloadSerializationException(String message) {
        super(message);
    }

    public PayloadSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
