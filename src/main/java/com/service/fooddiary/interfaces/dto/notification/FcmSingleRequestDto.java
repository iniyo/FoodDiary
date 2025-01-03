package com.service.fooddiary.interfaces.dto.notification;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public final class FcmSingleRequestDto {
    private final UUID requestId;
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank
    private final String userEmail;
    @NotNull(message = "contentId는 필수 값입니다.")
    @Positive(message = "contentId는 양수여야 합니다.") // contentId가 양수인지 확인
    private final Long contentId;
    @NotBlank(message = "notifyType은 필수 값입니다.")
    private final String notifyType;
    @NotBlank(message = "title은 필수 값입니다.")
    private final String title;
    @NotBlank(message = "content는 필수 값입니다.")
    private final String content;

    private FcmSingleRequestDto(String userEmail, Long contentId, String notifyType, String title, String content) {
        this.requestId = UUID.randomUUID();
        this.userEmail = userEmail;
        this.contentId = contentId;
        this.notifyType = notifyType;
        this.title = title;
        this.content = content;
    }

    public FcmSingleRequestDto of(String userEmail, Long contentId, String notifyType, String title, String content) {
        return new FcmSingleRequestDto(
                userEmail,
                contentId,
                notifyType,
                title,
                content
        );
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getUserEmail() {
        return userEmail;
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
