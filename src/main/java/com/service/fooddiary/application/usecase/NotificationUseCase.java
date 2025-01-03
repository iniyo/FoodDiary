package com.service.fooddiary.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.fooddiary.application.dto.FcmServiceCommand;
import com.service.fooddiary.domain.common.exception.domain.PayloadSerializationException;
import com.service.fooddiary.domain.notification.NotifyType;
import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.domain.user.model.vo.Email;
import com.service.fooddiary.domain.user.repository.UserRepository;
import com.service.fooddiary.infrastructure.messaging.payloads.NotificationEventPayload;
import com.service.fooddiary.infrastructure.persistence.entity.outbox.OutboxEntity;
import com.service.fooddiary.infrastructure.persistence.repository.JpaOutBoxRepository;
import com.service.fooddiary.infrastructure.utils.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
public class NotificationUseCase {

    private final UserRepository userRepository;
    private final JpaOutBoxRepository outboxRepository;  // Outbox 엔티티 저장용
    private final ObjectMapper objectMapper;          // JSON 변환

    public NotificationUseCase(UserRepository userRepository, JpaOutBoxRepository outboxRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
    }

    // 단일 사용자 알림
    public void sendNotification(FcmServiceCommand command, String email) {
        User user = userRepository.findByUserEmail(new Email(email));

        NotificationEventPayload payload = NotificationEventPayload.of(
                command.requestId(),
                command.contentId(),
                NotifyType.from(command.type()),
                command.title(),
                command.content(),
                command.userName(),
                user.getUserInfo().getToken()
        );

        try {
            String json = objectMapper.writeValueAsString(payload);
            OutboxEntity outbox = OutboxEntity.of(
                    "NOTIFICATION",                // aggregateType
                    payload.getToken().token(),    // aggregateId(예시)
                    "NotificationCreatedEvent",     // eventType
                    json
            );
            outboxRepository.save(outbox);
        } catch (JsonProcessingException e) {
            // 직렬화 오류 처리
            throw new PayloadSerializationException("Failed to serialize event payload", e);
        }
    }

    /**
     * DB 트랜잭션 안에서:
     * 1) userRepository 사용해 사용자 로직 처리
     * 2) Outbox에 이벤트 추가로 기록
     * 3) 트랜잭션 커밋 시 DB 변경사항 + Outbox 레코드가 동시에 확정됨
     */
    @Transactional
    public void sendMultipleNotification(FcmServiceCommand command) {
        // 1) 유저 목록 조회
        List<User> users = userRepository.findAll();

        // 2) 알림 이벤트(도메인 이벤트) 객체 or DTO를 만든 뒤, JSON 직렬화
        // 순서가 중요하지 않고 대용량 처리 이므로 parallelStream으로 처리
        List<NotificationEventPayload> payloadList = users.parallelStream()
                .map(user -> NotificationEventPayload.of(
                        command.requestId(),
                        command.contentId(),
                        NotifyType.from(command.type()),
                        command.title(),
                        command.content(),
                        null,
                        user.getUserInfo().getToken()
                ))
                .toList();

        // 3) 각 이벤트마다 Outbox 테이블에 기록
        for (NotificationEventPayload payload : payloadList) {
            try {
                String json = objectMapper.writeValueAsString(payload);
                OutboxEntity outbox = OutboxEntity.of(
                        "NOTIFICATION",                // aggregateType
                        payload.getRequestId().toString(),    // aggregateId(예시)
                        payload.getType().name(),     // eventType
                        json
                );
                outboxRepository.save(outbox);
            } catch (JsonProcessingException e) {
                // 직렬화 오류 처리
                throw new PayloadSerializationException("Failed to serialize event payload", e);
            }
        }
        // => 트랜잭션 커밋 시점에 Outbox 테이블에 레코드가 생성되어 published=0 상태가 됨
    }
}
