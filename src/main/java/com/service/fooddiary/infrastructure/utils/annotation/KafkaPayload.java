package com.service.fooddiary.infrastructure.utils.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KafkaPayload {
    String value() default "";
}
