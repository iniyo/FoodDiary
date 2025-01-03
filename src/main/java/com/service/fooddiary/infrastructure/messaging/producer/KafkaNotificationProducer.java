package com.service.fooddiary.infrastructure.messaging.producer;

import com.service.fooddiary.infrastructure.messaging.common.KafkaEvent;
import com.service.fooddiary.infrastructure.messaging.payloads.NotificationEventPayload;
import com.service.fooddiary.interfaces.dto.notification.FcmResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Component
@Slf4j
public class KafkaNotificationProducer {

    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public KafkaNotificationProducer(KafkaTemplate<String, KafkaEvent> kafkaTemplate, SimpMessagingTemplate messagingTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(KafkaEvent event) {
        CompletableFuture<?> future = kafkaTemplate.send(event.getTopic(), event);

        future.thenAccept(result -> log.info("NotificationEvent sent successfully: {}", event))
                .exceptionally(ex -> {
                    log.error("Failed to send NotificationEvent: {}", event, ex);
                    return null;
                });
    }

    public void sendNotificationEvent(NotificationEventPayload payload) {
        // NotificationEventPayload -> NotificationEvent 변환
        NotificationEventPayload event = NotificationEventPayload.of(
                payload.getRequestId(),
                payload.getContentId(),
                payload.getType(),
                payload.getTitle(),
                payload.getContent(),
                payload.getUserName(),
                payload.getToken()
        );

        kafkaTemplate.send(event.getTopic(), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send notification event: {}", event, ex);
                    } else {
                        log.info("NotificationEvent published successfully: {}", event);
                    }
                });
    }

    public void publishSuccess(UUID requestId, String msg) {
        // /topic/notificationResult 라는 채널로 브로드캐스트
        // 혹은 특정 사용자에게만 보내려면, /queue/ 뒤에 sessionId 등을 사용
        messagingTemplate.convertAndSend("/topic/notificationResult",
                new FcmResponseDto(requestId, "SUCCESS", msg));
    }

    public void publishFail(UUID requestId, String reason) {
        messagingTemplate.convertAndSend("/topic/notificationResult",
                new FcmResponseDto(requestId, "FAIL", reason));
    }
}