package com.service.fooddiary.interfaces.controller;

import com.service.fooddiary.application.usecase.NotificationUseCase;
import com.service.fooddiary.infrastructure.utils.ApiResult;
import com.service.fooddiary.interfaces.dto.notification.FcmMultiRequestDto;
import com.service.fooddiary.interfaces.dto.notification.FcmResponseDto;
import com.service.fooddiary.interfaces.dto.notification.FcmSingleRequestDto;
import com.service.fooddiary.interfaces.mapper.FcmMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fcm")
public class FcmController {

    private final NotificationUseCase notificationUseCase;
    private final FcmMapper fcmMapper;

    public FcmController(NotificationUseCase notificationUseCase, FcmMapper fcmMapper) {
        this.notificationUseCase = notificationUseCase;
        this.fcmMapper = fcmMapper;
    }

    @PostMapping("/single")
    public ResponseEntity<ApiResult<FcmResponseDto>> sendSingle(final @RequestBody @Valid FcmSingleRequestDto dto) {
        notificationUseCase.sendNotification(fcmMapper.reqToService(dto), dto.getUserEmail());
        return ResponseEntity.ok(ApiResult.ok(new FcmResponseDto(dto.getRequestId(), "전송 성공", null)));
    }

    @PostMapping("/multi")
    public ResponseEntity<ApiResult<FcmResponseDto>> sendMulti(final @RequestBody @Valid FcmMultiRequestDto dto) {
        notificationUseCase.sendMultipleNotification(fcmMapper.reqToService(dto));
        return ResponseEntity.ok(ApiResult.ok(new FcmResponseDto(dto.getRequestId(), "전송 성공", null)));
    }


}
