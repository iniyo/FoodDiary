package com.service.fooddiary.domain.user.model.enums;

import com.service.fooddiary.domain.common.EnumUtil;

public enum ActivityLevel {
    SEDENTARY,           // 좌식 생활
    LIGHTLY_ACTIVE,      // 가벼운 활동
    MODERATELY_ACTIVE,   // 중간 활동
    VERY_ACTIVE,         // 활발한 활동
    EXTREMELY_ACTIVE;    // 매우 활발한 활동

    public static ActivityLevel from(String value) {
        return EnumUtil.fromString(ActivityLevel.class, value);
    }
}
