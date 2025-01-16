package com.service.fooddiary.infrastructure.messaging.consumer;

import com.service.fooddiary.application.command.NotificationService;
import com.service.fooddiary.infrastructure.messaging.payloads.NotificationEventPayload;
import com.service.fooddiary.infrastructure.messaging.producer.KafkaNotificationProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class NotificationEventConsumer {

    private final NotificationService notificationService;
    private final KafkaNotificationProducer producer;

    public NotificationEventConsumer(NotificationService notificationService, KafkaNotificationProducer producer) {
        this.notificationService = notificationService;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "#{kafkaTopics.NOTIFICATION_TOPIC}",
            groupId = "#{kafkaTopics.NOTIFICATION_GROUP}"
    )
    public void handleNotificationEvent(NotificationEventPayload event) {
        try {
            // 실제 FCM 발송
            notificationService.sendByTokenNotification(event.toCommand(), event.getToken());
            // 성공
            producer.publishSuccess(event.getRequestId(), "FCM 발송 성공");
        } catch (Exception e) {
            // 실패
            producer.publishFail(event.getRequestId(), "FCM 발송 실패: " + e.getMessage());
        }
    }
}