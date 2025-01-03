package com.service.fooddiary.domain.common.exception.domain;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;

public class UnauthorizedException extends BusinessBaseException {
    public UnauthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
