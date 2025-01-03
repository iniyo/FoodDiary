package com.service.fooddiary.infrastructure.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.service.fooddiary.infrastructure.common.constants.basecode.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.service.fooddiary.infrastructure.common.constants.basecode.SuccessCode.CREATED;
import static com.service.fooddiary.infrastructure.common.constants.basecode.SuccessCode.OK;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {

    private String code;

    private String desc;

    @JsonInclude(Include.NON_EMPTY)
    private String message;

    @JsonInclude(Include.NON_NULL)
    private T data;

    public static <T> ApiResult<T> ok() {
        return ApiResult.<T>builder()
                .code(OK.getCode())
                .desc(OK.getDesc())
                .build();
    }

    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.<T>builder()
                .code(OK.getCode())
                .desc(OK.getDesc())
                .data(data)
                .build();
    }

    public static <T> ApiResult<T> created() {
        return ApiResult.<T>builder()
                .code(CREATED.getCode())
                .desc(CREATED.getDesc())
                .build();
    }

    public static <T> ApiResult<T> response(
            ResponseCode responseCode) {
        return ApiResult.<T>builder()
                .code(responseCode.getCode())
                .desc(responseCode.getDesc())
                .build();
    }

    public static <T> ApiResult<T> response(ResponseCode responseCode, String message) {
        return ApiResult.<T>builder()
                .code(responseCode.getCode())
                .desc(responseCode.getDesc())
                .message(message)
                .build();
    }

    public static <T> ApiResult<T> response(ResponseCode responseCode, T data) {
        return ApiResult.<T>builder()
                .code(responseCode.getCode())
                .desc(responseCode.getDesc())
                .data(data)
                .build();
    }

    public static <T> ApiResult<T> response(ResponseCode responseCode, String message, T data) {
        return ApiResult.<T>builder()
                .code(responseCode.getCode())
                .desc(responseCode.getDesc())
                .message(message)
                .data(data)
                .build();
    }
}
