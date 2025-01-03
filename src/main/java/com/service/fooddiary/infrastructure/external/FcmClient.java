package com.service.fooddiary.infrastructure.external;

import com.google.firebase.messaging.*;
import com.service.fooddiary.application.dto.FcmServiceCommand;
import com.service.fooddiary.domain.user.model.vo.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FcmClient {

    private final FirebaseMessaging firebaseMessaging;

    public FcmClient(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendByToken(FcmServiceCommand dto, Token token) {
        Message message = buildMessageWithToken(dto, token.token());
        try {
            String response = firebaseMessaging.send(message);
            log.info("FCM Message sent successfully: Response={}, Token={}", response, token);
        } catch (FirebaseMessagingException e) {
            handleFirebaseMessagingException(e, token);
        }
    }

    public void sendByMultiple(FcmServiceCommand dto, List<Token> tokens) {
        MulticastMessage message = buildMulticastMessage(dto, tokens.stream().map(Token::token).toList());

        try {
            BatchResponse batchResponse = firebaseMessaging.sendEachForMulticast(message);
            log.info("FCM Message sent successfully: Response={}", batchResponse.getResponses());
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send FCM message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send FCM message", e);
        }
    }

    // FirebaseMessagingException 처리
    private void handleFirebaseMessagingException(FirebaseMessagingException e, Token token) {
        if ("UNREGISTERED".equals(e.getMessagingErrorCode().name())) {
            log.warn("Invalid Token detected: {}", token);
            // 필요 시 데이터베이스에서 해당 토큰 제거 로직 추가
        } else {
            log.error("FirebaseMessagingException occurred: {}", e.getMessage(), e);
        }
    }

    private Message buildMessageWithToken(FcmServiceCommand dto, String token) {
        return Message.builder()
                .setToken(token)
                .setNotification(buildNotification(dto))
                .setAndroidConfig(buildAndroidConfig(dto))
                .setApnsConfig(buildApnsConfig())
                .putAllData(buildDataMap(dto))
                .build();
    }

    private MulticastMessage buildMulticastMessage(FcmServiceCommand dto, List<String> tokens) {
        return MulticastMessage.builder()
                .addAllTokens(tokens)
                .setNotification(buildNotification(dto))
                .setAndroidConfig(buildAndroidConfig(dto))
                .setApnsConfig(buildApnsConfig())
                .putAllData(buildDataMap(dto))
                .build();
    }

    private Notification buildNotification(FcmServiceCommand dto) {
        return Notification.builder()
                .setTitle(dto.title())
                .setBody(dto.content())
                .build();
    }

    private AndroidConfig buildAndroidConfig(FcmServiceCommand dto) {
        return AndroidConfig.builder()
                .setNotification(
                        AndroidNotification.builder()
                                .setTitle(dto.title())
                                .setBody(dto.content())
                                .setClickAction("push_click")
                                .build()
                )
                .build();
    }

    private ApnsConfig buildApnsConfig() {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory("push_click")
                        .build())
                .build();
    }

    private Map<String, String> buildDataMap(FcmServiceCommand dto) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("type", dto.type());
        dataMap.put("contentId", dto.contentId().toString());
        return dataMap;
    }
}
