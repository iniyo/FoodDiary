package com.service.fooddiary.infrastructure.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 클래스 레벨에 적용
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorResponses {
    ApiErrorResponse[] value();
}
