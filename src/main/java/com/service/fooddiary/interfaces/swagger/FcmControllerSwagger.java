package com.service.fooddiary.interfaces.swagger;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import static com.service.fooddiary.interfaces.swagger.SwaggerUtils.addErrorResponses;


@Component
class FcmControllerSwagger implements OperationCustomizer {

    public FcmControllerSwagger(SwaggerUtils utils) {
        utils.addDtoSchema("FcmSingleRequestDto", FcmSingleRequestDtoSchema());
        utils.addDtoSchema("FcmMultiRequestDto", FcmMultiRequestDtoSchema());
        utils.addDtoSchema("FcmResponseDto", FcmResponseDtoSchema());
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        String methodName = handlerMethod.getMethod().getName();

        operation.setTags(List.of("Notification API")); // 중복 제거를 위해 단일 태그 설정

        if ("sendSingle".equals(methodName)) {
            operation.setSummary("단일 사용자 알림 전송");
            operation.setDescription("단일 사용자에게 FCM 알림을 전송합니다.");
            addErrorResponses(operation, ErrorCode.BAD_REQUEST, ErrorCode.INTERNAL_SERVER_ERROR);
        } else if ("sendMulti".equals(methodName)) {
            operation.setSummary("다중 사용자 알림 전송");
            operation.setDescription("다중 사용자에게 FCM 알림을 전송합니다.");
            addErrorResponses(operation, ErrorCode.BAD_REQUEST, ErrorCode.INTERNAL_SERVER_ERROR);
        }

        return operation;
    }

    public static Schema<?> FcmSingleRequestDtoSchema() {
        // 필드 정의
        Map<String, Schema<?>> properties = new LinkedHashMap<>();
        properties.put("userEmail", new Schema<String>().type("string").description("사용자의 이메일").example("test322@gmail.com"));
        properties.put("contentId", new Schema<Long>().type("integer").format("int64").description("컨텐츠 ID").example(12345L));
        properties.put("notifyType", new Schema<String>().type("string").description("알림 유형").example("NOTICE"));
        properties.put("title", new Schema<String>().type("string").description("알림 제목").example("New Notification"));
        properties.put("content", new Schema<String>().type("string").description("알림 내용").example("You have a new message!"));

        // Schema 객체 생성 및 설정
        Schema<Object> schema = new Schema<>();
        schema.setType("object");
        schema.setDescription("FCM 요청 DTO");
        schema.setProperties(Collections.unmodifiableMap(properties)); // 불변 Map
        return schema;
    }

    public static Schema<?> FcmMultiRequestDtoSchema() {
        // 필드 정의
        Map<String, Schema<?>> properties = new LinkedHashMap<>();
        properties.put("contentId", new Schema<Long>().type("integer").format("int64").description("컨텐츠 ID").example(12345L));
        properties.put("notifyType", new Schema<String>().type("string").description("알림 유형").example("NOTICE"));
        properties.put("title", new Schema<String>().type("string").description("알림 제목").example("New Notification"));
        properties.put("content", new Schema<String>().type("string").description("알림 내용").example("You have a new message!"));

        // Schema 객체 생성 및 설정
        Schema<Object> schema = new Schema<>();
        schema.setType("object");
        schema.setDescription("FCM 요청 DTO");
        schema.setProperties(Collections.unmodifiableMap(properties)); // 불변 Map
        return schema;
    }

    public static Schema<?> FcmResponseDtoSchema() {
        // 필드 정의
        Map<String, Schema<?>> properties = new LinkedHashMap<>();
        properties.put("requestId", new Schema<Long>().type("uuid").description("알림 요청 ID").example(12345L));
        properties.put("success", new Schema<String>().type("string").description("알림 유형").example("NOTICE"));
        properties.put("message", new Schema<String>().type("string").description("알림 제목").example("New Notification"));

        // Schema 객체 생성 및 설정
        Schema<Object> schema = new Schema<>();
        schema.setType("object");
        schema.setDescription("FCM 요청 DTO");
        schema.setProperties(Collections.unmodifiableMap(properties)); // 불변 Map
        return schema;
    }
}