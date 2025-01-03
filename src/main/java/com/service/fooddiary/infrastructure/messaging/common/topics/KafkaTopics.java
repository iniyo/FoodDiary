package com.service.fooddiary.infrastructure.messaging.common.topics;

import org.springframework.stereotype.Component;

@Component
public class KafkaTopics {
    // Notification 관련 토픽
    public static final String NOTIFICATION_GROUP = "notification-group";
    public static final String NOTIFICATION_TOPIC = "notification-events";

    // 다른 Kafka 이벤트 토픽
    public static final String USER_ACTIVITY_TOPIC = "user-activity-events";
}
