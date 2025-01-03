package com.service.fooddiary.infrastructure.messaging.common;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.service.fooddiary.infrastructure.messaging.payloads.NotificationEventPayload;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
@JsonSubTypes({
        // "notificationEvent" 등 name은 자유롭게
        @JsonSubTypes.Type(value = NotificationEventPayload.class, name = "notificationEvent")
        // 예: 추가 이벤트가 있다면 여기에 @JsonSubTypes.Type(...)로 등록
        // @JsonSubTypes.Type(value = PaymentEventPayload.class, name = "paymentEvent")
})
public abstract class KafkaEvent {

    private String topic;

    public KafkaEvent(String topic) {
        this.topic = topic;
    }

    /**
     * Kafka 이벤트가 사용할 Topic 이름 반환.
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Kafka 이벤트의 메시지 반환.
     * 기본 메시지 또는 JSON 문자열 직렬화.
     */
    public String getMessage() {
        return "{}"; // 기본값으로 빈 JSON 반환
    }

    /**
     * Kafka 이벤트의 토픽 이름을 변경 가능.
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
}
