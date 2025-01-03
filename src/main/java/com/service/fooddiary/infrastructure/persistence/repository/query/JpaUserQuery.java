package com.service.fooddiary.infrastructure.persistence.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.service.fooddiary.application.dto.UserServiceQuery;
import com.service.fooddiary.application.query.UserQueryRepository;
import com.service.fooddiary.domain.common.exception.domain.NotAvailableException;
import com.service.fooddiary.domain.common.exception.domain.NotFoundException;
import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.infrastructure.persistence.entity.user.QUserEntity;
import com.service.fooddiary.infrastructure.persistence.entity.user.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
class JpaUserQuery implements UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public JpaUserQuery(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<User> findUsersByDynamicCondition(UserServiceQuery queryDto) {
        // 쿼리 DSL 메타모델 클래스
        QUserEntity user = QUserEntity.userEntity;

        // 검증 로직: all이 false이고 조건이 없을 때 예외 발생
        if (!queryDto.all() && queryDto.isType() == UserServiceQuery.Type.ONE_ATTRIBUTE) {
            throw new NotAvailableException("검색 조건이 없습니다. 적어도 하나의 조건을 입력하세요.");
        }

        // 조건 생성
        BooleanBuilder condition = createCondition(user, queryDto);

        // 쿼리 실행
        List<User> users = queryFactory.selectFrom(user)
                .where(condition)
                .fetch()
                .stream()
                .map(UserEntity::toDomain)
                .toList();

        if (users.isEmpty()) {
            throw new NotFoundException("조건에 맞는 사용자를 찾을 수 없습니다.");
        }

        return users;
    }

    // 동적 조건 생성 로직
    private BooleanBuilder createCondition(QUserEntity user, UserServiceQuery queryDto) {
        BooleanBuilder condition = new BooleanBuilder();

        if (queryDto.all()) {
            return condition; // all=true일 경우 조건 없이 전체 데이터 반환
        }

        // 각 필드에 대한 조건 추가
        addCondition(condition, user.userInfoEntity.email, queryDto.email());
        addCondition(condition, user.userInfoEntity.userName, queryDto.name());
        addCondition(condition, user.userInfoEntity.phoneNumber, queryDto.phoneNumber());

        return condition;
    }

    // 단일 필드에 대한 조건 추가 - QueryDsl은 VARCHAR,TEXT 등 문자열 타입이라면 StringPath객체로 표현됨.
    private void addCondition(BooleanBuilder builder, StringPath field, String value) {
        if (value != null && !value.isBlank()) {
            builder.and(field.eq(value));
        }
    }

}
