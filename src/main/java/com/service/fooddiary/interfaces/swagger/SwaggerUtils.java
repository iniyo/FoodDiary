package com.service.fooddiary.interfaces.swagger;

import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorCode;
import com.service.fooddiary.infrastructure.common.constants.basecode.ErrorResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class SwaggerUtils {

    private final Components components;

    public SwaggerUtils(Components components) {
        this.components = components;
    }

    public void addDtoSchema(String name, Schema<?> schema) {
        components.addSchemas(name, schema);
    }

    /**
     * API 응답에 오류 상태를 추가
     *
     * @param operation  Swagger Operation 객체
     * @param errorCodes 추가할 ErrorCode 리스트
     */
    public static void addErrorResponses(Operation operation, ErrorCode... errorCodes) {
        for (ErrorCode errorCode : errorCodes) {
            operation.getResponses().addApiResponse(
                    String.valueOf(errorCode.getHttpStatus().value()),
                    createApiResponse(errorCode)
            );
        }
    }

    /**
     * 단일 API 응답 생성
     *
     * @param errorCode ErrorCode 객체
     * @return ApiResponse 객체
     */

    public static ApiResponse createApiResponse(ErrorCode errorCode) {
        Object example = generateExample(errorCode);

        return new ApiResponse()
                .description(errorCode.getDesc())
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>().example(example))));
    }


    private static Object generateExample(ErrorCode errorCode) {
        if (errorCode == ErrorCode.BAD_REQUEST) {
            return ErrorResponse.of(
                    ErrorCode.BAD_REQUEST,
                    Map.of("field", "reason") // 필드 에러 포함
            );
        } else {
            return ErrorResponse.of(errorCode); // 단순 에러 메시지
        }
    }

    public static Schema<Object> apiResultSchema(String dataSchemaName) {
        Map<String, Schema<?>> properties = new LinkedHashMap<>(); // LinkedHashMap 사용

        properties.put("code", new Schema<>().type("string").example("20000000"));
        properties.put("desc", new Schema<>().type("string").example("Success"));
        properties.put("message", new Schema<>().type("string").example("요청 성공"));
        properties.put("data", new Schema<>().$ref("#/components/schemas/" + dataSchemaName));

        Schema<Object> schema = new Schema<>();
        schema.setType("object");
        schema.setDescription("API 공통 응답");
        schema.setProperties(Collections.unmodifiableMap(properties)); // 불변 Map

        return schema;
    }
}
