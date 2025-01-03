package com.service.fooddiary.infrastructure.utils.annotation;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponse {
    ErrorCode errorCode();
}
