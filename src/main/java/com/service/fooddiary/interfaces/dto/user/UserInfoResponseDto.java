package com.service.fooddiary.interfaces.dto.user;

public record UserInfoResponseDto(
        String email,
        String name,
        String phoneNumber
) {
}
