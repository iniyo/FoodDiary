package com.service.fooddiary.interfaces.dto.user;

public record UserInfoRequestDto(
        String email,
        String name,
        String phoneNumber,
        Boolean all
) {
}
