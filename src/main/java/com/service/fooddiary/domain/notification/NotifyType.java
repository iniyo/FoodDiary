package com.service.fooddiary.domain.notification;

public enum NotifyType {
    PUSH,
    EMAIL,
    SMS,
    NOTICE;

    public static NotifyType from(String value) {
        try {
            return NotifyType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid NotifyType: " + value);
        }
    }
}
