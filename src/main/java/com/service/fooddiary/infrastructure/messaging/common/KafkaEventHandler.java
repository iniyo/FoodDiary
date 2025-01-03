package com.service.fooddiary.infrastructure.messaging.common;

public interface KafkaEventHandler<T extends KafkaEvent> {

    /**
     * 핸들러가 처리할 이벤트 타입(예: NotificationEventPayload.class)을 반환
     */
    Class<T> getEventType();

    /**
     * 실제 메시징 처리/카프카 발행 로직
     */
    void handle(T event);
}