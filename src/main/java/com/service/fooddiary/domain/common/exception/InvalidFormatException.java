package com.service.fooddiary.domain.common.exception;


import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;

public class InvalidFormatException extends InputValidationException {
    public InvalidFormatException(String fieldName) {
        super(fieldName + " has an invalid format.", ErrorCode.VALIDATION_FAILED);
    }
}