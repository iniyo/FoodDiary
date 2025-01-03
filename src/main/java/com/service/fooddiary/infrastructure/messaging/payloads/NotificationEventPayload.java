package com.service.fooddiary.infrastructure.messaging.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.service.fooddiary.application.dto.FcmServiceCommand;
import com.service.fooddiary.domain.notification.NotifyType;
import com.service.fooddiary.domain.user.model.vo.Token;
import com.service.fooddiary.infrastructure.messaging.common.KafkaEvent;
import com.service.fooddiary.infrastructure.messaging.common.topics.KafkaTopics;
import lombok.Getter;

import java.util.UUID;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 필드를 JSON에서 제외
public class NotificationEventPayload extends KafkaEvent {

    private final UUID requestId;
    private final Long contentId; // optional
    private final NotifyType type;
    private final String title;
    private final String content;
    private final String userName; // optional
    private final Token token;

    // Default Constructor for Kafka Serialization/Deserialization
    private NotificationEventPayload(UUID requestId, Long contentId, NotifyType type, String title, String content, String userName, Token token) {
        super(KafkaTopics.NOTIFICATION_TOPIC);
        this.requestId = requestId;
        this.contentId = contentId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.token = token;
    }

    public static NotificationEventPayload of(UUID requestId, NotifyType type, String title, String content, Token token) {
        return new NotificationEventPayload(requestId, null, type, title, content, null, token);
    }

    public static NotificationEventPayload of(UUID requestId, NotifyType type, String title, String content, String userName, Token token) {
        return new NotificationEventPayload(requestId, null, type, title, content, userName, token);
    }

    public static NotificationEventPayload of(UUID requestId, Long contentId, NotifyType type, String title, String content, String userName, Token token) {
        return new NotificationEventPayload(requestId, contentId, type, title, content, userName, token);
    }

    @Override
    public String getMessage() {
        return String.format("{\"title\":\"%s\",\"content\":\"%s\"}", title, content);
    }

    public FcmServiceCommand toCommand() {
        return new FcmServiceCommand(
                requestId,
                contentId != null ? contentId : 0,
                type.name(),
                title,
                content,
                userName != null ? userName : ""
        );
    }

    @Override
    public String toString() {
        return "NotificationEvent{" +
                "contentId=" + contentId +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", token=" + token +
                '}';
    }
}