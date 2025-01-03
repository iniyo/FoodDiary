package com.service.fooddiary.infrastructure.persistence.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.fooddiary.infrastructure.messaging.common.KafkaEventHandler;
import com.service.fooddiary.infrastructure.messaging.common.KafkaEventHandlerRegistry;
import com.service.fooddiary.infrastructure.messaging.common.KafkaEvent;
import com.service.fooddiary.infrastructure.persistence.entity.outbox.OutboxEntity;
import com.service.fooddiary.infrastructure.persistence.repository.JpaOutBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final JpaOutBoxRepository outboxRepository;
    private final KafkaEventHandlerRegistry handlerRegistry;
    private final ObjectMapper objectMapper;

    private static final int BATCH_SIZE = 50;
    private static final int MAX_RETRY_COUNT = 5;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    @SuppressWarnings("unchecked")
    public void processOutbox() {
        PageRequest pageRequest = PageRequest.of(0, BATCH_SIZE, Sort.by(Sort.Direction.ASC, "id"));
        List<OutboxEntity> outboxList = outboxRepository.findUnpublishedForUpdate(pageRequest);

        if (outboxList.isEmpty()) {
            log.debug("No unpublished outbox records found.");
            return;
        }

        for (OutboxEntity outbox : outboxList) {
            try {
                // 역직렬화: KafkaEvent 부모타입
                KafkaEvent event = objectMapper.readValue(outbox.getPayload(), KafkaEvent.class);

                // 실제 런타임 클래스 (ex: NotificationEventPayload.class)
                Class<? extends KafkaEvent> runtimeClass = event.getClass();

                // registry에서 핸들러를 가져옴
                KafkaEventHandler<KafkaEvent> handler =
                        (KafkaEventHandler<KafkaEvent>) handlerRegistry.getHandler(runtimeClass);

                if (handler == null) {
                    log.warn("No handler found for event class: {} => skipping", runtimeClass.getName());
                } else {
                    // 최종 처리
                    handler.handle(event);
                }

                // 발행 성공
                outbox.markAsPublished();

            } catch (Exception e) {
                // 에러 처리 로직...
                log.error("Failed to publish outbox record id={}, error={}", outbox.getId(), e.getMessage(), e);

                outbox.incrementRetryCount();
                if (!outbox.canRetry(MAX_RETRY_COUNT)) {
                    outbox.markAsDeadLetter();
                    log.error("Outbox record id={} moved to deadLetter.", outbox.getId());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanUpOutbox() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(7);
        int deletedPublished = outboxRepository.deleteOldPublishedRecords(threshold);
        int deletedDead = outboxRepository.deleteOldDeadLetterRecords(threshold);

        log.info("Outbox cleanup done. deletedPublished={}, deletedDead={}", deletedPublished, deletedDead);
    }
}
