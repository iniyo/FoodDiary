package com.service.fooddiary.application.command;

import com.service.fooddiary.application.dto.FcmServiceCommand;
import com.service.fooddiary.domain.user.model.vo.Token;
import com.service.fooddiary.infrastructure.external.FcmClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final FcmClient fcmClient;

    public void sendByTokenNotification(FcmServiceCommand dto, Token token) {
        fcmClient.sendByToken(dto, token);
    }
}