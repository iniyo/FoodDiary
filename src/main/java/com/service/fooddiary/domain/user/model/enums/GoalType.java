package com.service.fooddiary.domain.user.model.enums;

import com.service.fooddiary.domain.common.EnumUtil;

public enum GoalType {
    WEIGHT_LOSS,    // 체중 감량
    WEIGHT_MAINTENANCE, // 체중 유지
    WEIGHT_GAIN;     // 체중 증량

    public static GoalType from(String value) {
        return EnumUtil.fromString(GoalType.class, value);
    }
}
