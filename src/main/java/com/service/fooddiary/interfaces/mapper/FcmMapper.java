package com.service.fooddiary.interfaces.mapper;

import com.service.fooddiary.application.dto.FcmServiceCommand;
import com.service.fooddiary.interfaces.dto.notification.FcmMultiRequestDto;
import com.service.fooddiary.interfaces.dto.notification.FcmSingleRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FcmMapper {

    public FcmServiceCommand reqToService(FcmSingleRequestDto dto) {
        return constructor(dto.getRequestId(), dto.getNotifyType(), dto.getTitle(), dto.getContent());
    }

    public FcmServiceCommand reqToService(FcmMultiRequestDto dto) {
        return constructor(dto.getRequestId(), dto.getNotifyType(), dto.getTitle(), dto.getContent());
    }

    private FcmServiceCommand constructor(UUID requestId, String notifyType, String title, String content) {
        return new FcmServiceCommand(requestId, null, notifyType, title, content, null);
    }
}
