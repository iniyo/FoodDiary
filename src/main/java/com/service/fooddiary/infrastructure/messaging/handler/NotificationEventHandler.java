package com.service.fooddiary.infrastructure.messaging.handler;

import com.service.fooddiary.infrastructure.messaging.common.KafkaEventHandler;
import com.service.fooddiary.infrastructure.messaging.payloads.NotificationEventPayload;
import com.service.fooddiary.infrastructure.messaging.producer.KafkaNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventHandler implements KafkaEventHandler<NotificationEventPayload> {

    private final KafkaNotificationProducer producer;

    @Override
    public Class<NotificationEventPayload> getEventType() {
        // "이 핸들러는 NotificationEventPayload"를 처리
        return NotificationEventPayload.class;
    }

    @Override
    public void handle(NotificationEventPayload event) {
        // 카프카 발행 등 알림 처리 로직
        producer.sendNotificationEvent(event);
    }
}