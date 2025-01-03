package com.service.fooddiary.infrastructure.persistence.entity.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Getter
@NoArgsConstructor
@ToString
public class OutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;  // 예: "NOTIFICATION"

    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;    // 예: "userId=12345"

    @Column(name = "event_type", nullable = false)
    private String eventType;      // 예: "NotificationCreatedEvent"

    @Lob
    @Column(name = "payload", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String payload;        // 이벤트 데이터(JSON 직렬화)

    @Column(name = "published", nullable = false)
    private boolean published;     // 이미 발행 성공했는지 여부

    /**
     * 재시도 횟수. 기본 0
     */
    @Column(name = "retry_count", nullable = false)
    private int retryCount = 0;

    /**
     * 재시도 제한 초과 등으로 Dead Letter 상태에 들어간 경우 true
     */
    @Column(name = "dead_letter", nullable = false)
    private boolean deadLetter = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        this.published = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    private OutboxEntity(String aggregateType,
                        String aggregateId,
                        String eventType,
                        String payload) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.published = false;
        this.retryCount = 0;
        this.deadLetter = false;
    }

    public static OutboxEntity of(String aggregateType,
                           String aggregateId,
                           String eventType,
                           String payload) {
        return new OutboxEntity(
                aggregateType,
                aggregateId,
                eventType,
                payload
        );
    }

    public void markAsPublished() {
        this.published = true;
    }

    public void incrementRetryCount() {
        this.retryCount++;
    }

    public boolean canRetry(int maxRetry) {
        return this.retryCount < maxRetry;
    }

    public void markAsDeadLetter() {
        this.deadLetter = true;
    }
}

