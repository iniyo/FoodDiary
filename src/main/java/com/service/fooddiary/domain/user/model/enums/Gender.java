package com.service.fooddiary.domain.user.model.enums;

import com.service.fooddiary.domain.common.EnumUtil;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender from(String value) {
        return EnumUtil.fromString(Gender.class, value);
    }
}
