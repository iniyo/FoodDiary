package com.service.fooddiary.application.dto;

import com.service.fooddiary.domain.common.exception.InputValidationException;

import java.util.stream.Stream;

public record UserServiceQuery(
        String email,
        String name,
        String phoneNumber,
        boolean all
) {

    public enum Type {
        EMAIL,
        NAME,
        PHONE_NUMBER,
        ONE_ATTRIBUTE,
        TWO_ATTRIBUTE,
        ALL_ATTRIBUTE;
    }

    public Type isType() {
        // 비어 있지 않은 필드를 리스트로 수집
        var nonNullFields = Stream.of(email, name, phoneNumber)
                .filter(field -> !field.isBlank())
                .toList();

        int nonNullCount = nonNullFields.size();

        // 비어 있지 않은 필드 개수에 따라 타입 반환
        if (nonNullCount == 3) return Type.ALL_ATTRIBUTE;
        if (nonNullCount == 2) return Type.TWO_ATTRIBUTE;
        if (nonNullCount == 1) {
            String fieldName = nonNullFields.get(0);
            if (fieldName.equals(email)) return Type.EMAIL;
            if (fieldName.equals(name)) return Type.NAME;
            return Type.PHONE_NUMBER;
        }

        throw new InputValidationException("적어도 하나의 사용자 정보입력 또는 전체 요청을 해주세요");
    }
}