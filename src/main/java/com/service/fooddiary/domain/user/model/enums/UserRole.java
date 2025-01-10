package com.service.fooddiary.domain.user.model.enums;

import com.service.fooddiary.domain.common.EnumUtil;

public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MODERATOR;

    public static UserRole from(String value) {
        return EnumUtil.fromString(UserRole.class, value);
    }
}