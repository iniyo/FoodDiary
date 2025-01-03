package com.service.fooddiary.infrastructure.utils.annotation;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // Runtime 동안만 유지되도록
@Service // Service bean으로 관리
@Validated
public @interface UseCaseService {
}
