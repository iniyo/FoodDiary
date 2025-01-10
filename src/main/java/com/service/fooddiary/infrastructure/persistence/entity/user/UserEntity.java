package com.service.fooddiary.infrastructure.persistence.entity.user;

import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.domain.user.model.vo.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users") // 데이터베이스 테이블 이름
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Embedded
    private UserInfoEntity userInfoEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_body_info_id")
    private UserBodyInfoEntity userBodyInfoEntity;

    private UserEntity() {
    }

    private UserEntity(UUID userId, UserInfoEntity userInfoEntity, UserBodyInfoEntity userBodyInfoEntity) {
        this.userId = userId;
        this.userInfoEntity = userInfoEntity;
        this.userBodyInfoEntity = userBodyInfoEntity;
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getUserId().id(),
                UserInfoEntity.fromDomain(user.getUserInfo()),
                UserBodyInfoEntity.fromDomain()
        );
    }

    public User toDomain() {
        return User.of(
                new UserId(userId),
                userInfoEntity.toDomain(),
                userBodyInfoEntity != null ? userBodyInfoEntity.toDomain() : null,
                null
        );
    }

}
