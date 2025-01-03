package com.service.fooddiary.interfaces.dto.notification;

import java.util.UUID;

public record FcmResponseDto(
        UUID requestId,
        String success,
        String message
) {
}
