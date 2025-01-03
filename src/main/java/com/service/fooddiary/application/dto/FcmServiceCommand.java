package com.service.fooddiary.application.dto;

import java.util.UUID;

public record FcmServiceCommand(
        UUID requestId,
        Long contentId,
        String type,
        String title,
        String content,
        String userName
) {
}
