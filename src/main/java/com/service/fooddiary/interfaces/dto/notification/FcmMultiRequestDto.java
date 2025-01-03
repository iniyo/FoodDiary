package com.service.fooddiary.interfaces.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public final class FcmMultiRequestDto {
    private final UUID requestId;
    @NotNull(message = "contentId는 필수 값입니다.")
    private final Long contentId;
    @NotBlank(message = "notifyType은 필수 값입니다.")
    private final String notifyType;
    @NotBlank(message = "title은 필수 값입니다.")
    private final String title;
    @NotBlank(message = "content는 필수 값입니다.")
    private final String content;

    private FcmMultiRequestDto(Long contentId, String notifyType, String title, String content) {
        this.requestId = UUID.randomUUID();
        this.contentId = contentId;
        this.notifyType = notifyType;
        this.title = title;
        this.content = content;
    }

    public FcmMultiRequestDto of(Long contentId, String notifyType, String title, String content) {
        return new FcmMultiRequestDto(contentId, notifyType, title, content);
    }

    public UUID getRequestId() {
        return requestId;
    }

    public Long getContentId() {
        return contentId;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

