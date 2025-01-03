package com.service.fooddiary.infrastructure.persistence.entity.user;

import com.service.fooddiary.domain.user.model.entity.UserAccountInfo;
import com.service.fooddiary.domain.user.model.vo.*;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Embeddable
public class UserInfoEntity {

    private String userName;
    private String email;
    private String phoneNumber;
    private LocalDate createdAt;
    private String token;

    private UserInfoEntity() {
    }

    private UserInfoEntity(String userName, String email, String phoneNumber, String token, LocalDate createdAt) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.token = token;
        this.createdAt = createdAt;
    }

    public static UserInfoEntity fromDomain(UserAccountInfo userInfo) {
        return new UserInfoEntity(
                userInfo.getUserName().name(),
                userInfo.getEmail().email(),
                userInfo.getPhoneNumber().phoneNumber(),
                userInfo.getToken().token(),
                userInfo.getCreatedAt()
        );
    }

    public UserAccountInfo toDomain() {
        return UserAccountInfo.of(
                new UserName(userName),
                new Email(email),
                new PhoneNumber(phoneNumber),
                new Token(token),
                createdAt
        );
    }
}
