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

import static com.service.fooddiary.interfaces.swagger.SwaggerUtils.*;


@Component
class UserControllerSwagger implements OperationCustomizer {

    public UserControllerSwagger(SwaggerUtils utils) {
        utils.addDtoSchema("UserInfoRequestDto", userInfoRequestDtoSchema());
        utils.addDtoSchema("UserInfoResponseDto", userInfoResponseDtoSchema());
        utils.addDtoSchema("ApiResultListUserInfoResponseDto", apiResultSchema("UserInfoResponseDto"));
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        if ("AllUserForAttribute".equals(handlerMethod.getMethod().getName())) {
            operation.setSummary("사용자 정보 조회");
            operation.setDescription("사용자 정보를 조회하는 API입니다.");
            operation.setTags(List.of("User API"));

            addErrorResponses(operation, ErrorCode.BAD_REQUEST, ErrorCode.UNAUTHORIZED_TOKEN_EXPIRED,
                    ErrorCode.NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return operation;
    }

    public static Schema<?> userInfoRequestDtoSchema() {
        // 필드 정의
        Map<String, Schema<?>> properties = new LinkedHashMap<>();
        properties.put("email", new Schema<String>().type("string").description("사용자 이메일 주소").example("test322@gmail.com"));
        properties.put("name", new Schema<String>().type("string").description("사용자 이름").example("tester"));
        properties.put("phoneNumber", new Schema<String>().type("string").description("사용자 전화번호").example("010-1234-5678"));
        properties.put("all", new Schema<String>().type("boolean").description("모든 사용자 검색, default null").example("false").nullable(true));

        // Schema 객체 생성 및 설정
        Schema<Object> schema = new Schema<>();
        schema.setType("object");
        schema.setDescription("사용자 정보 요청 DTO");
        schema.setProperties(Collections.unmodifiableMap(properties)); // 불변 Map
        return schema;
    }

    public static Schema<?> userInfoResponseDtoSchema() {
        // 필드 정의
        Map<String, Schema<?>> properties = new LinkedHashMap<>();
        properties.put("email", new Schema<String>().type("string").description("사용자의 이메일 주소").example("test322@gmail.com"));
        properties.put("name", new Schema<String>().type("string").description("사용자의 이름").example("tester"));
        properties.put("phoneNumber", new Schema<String>().type("string").description("사용자의 전화번호").example("010-1234-5678"));

        // Schema 객체 생성 및 설정
        Schema<Object> schema = new Schema<>();
        schema.type("object");
        schema.description("사용자 정보 응답 DTO");
        schema.properties(Collections.unmodifiableMap(properties));
        return schema;
    }
}