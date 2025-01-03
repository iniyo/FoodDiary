package com.service.fooddiary.domain.common.exception.domain;


import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;

public class NotAvailableException extends BusinessBaseException {
    public NotAvailableException(String message) {
        super(message, ErrorCode.NOT_AVAILABLE);
    }

    public NotAvailableException() {
        super(ErrorCode.NOT_AVAILABLE);
    }
}
