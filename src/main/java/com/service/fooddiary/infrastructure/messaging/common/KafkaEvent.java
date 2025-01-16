package com.service.fooddiary.infrastructure.messaging.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.service.fooddiary.infrastructure.utils.annotation.KafkaPayload;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class KafkaEvent {

    /**
     * Kafka 이벤트의 메시지 반환.
     * 기본 메시지 또는 JSON 문자열 직렬화.
     */
    public String getMessage() {
        return "{}"; // 기본값으로 빈 JSON 반환
    }

    /**
     * 이 이벤트가 어떤 구체 클래스로 만들어졌는지 반환
     * (ex: NotificationEventPayload.class)
     */
    public Class<? extends KafkaEvent> getConcreteClass() {
        return this.getClass();
    }

    /**
     * 어노테이션(@KafkaPayload)에 지정된 값이 있으면 반환,
     * 없으면 간단히 클래스 이름 사용
     */
    public String getEventType() {
        KafkaPayload anno = this.getClass().getAnnotation(KafkaPayload.class);
        return (anno != null && !anno.value().isEmpty())
                ? anno.value()
                : this.getClass().getSimpleName(); // 혹은 "UNKNOWN"
    }
}
