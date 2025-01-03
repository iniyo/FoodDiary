package com.service.fooddiary.domain.common.exception.domain;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;

public class ValidationException extends BusinessBaseException {
    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_FAILED);
    }
}