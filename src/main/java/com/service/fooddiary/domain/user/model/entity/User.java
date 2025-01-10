package com.service.fooddiary.domain.user.model.entity;

import com.service.fooddiary.domain.common.AggregateRoot;
import com.service.fooddiary.domain.user.model.enums.UserRole;
import com.service.fooddiary.domain.user.model.vo.UserId;

public class User extends AggregateRoot<User> {

    private UserId userId;
    private UserAccountInfo userInfo;
    private UserBodyInfo userBodyInfo;
    private UserRole role;
    private User() {}

    private User(UserId userId, UserAccountInfo userInfo, UserBodyInfo userBodyInfo, UserRole role) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.userBodyInfo = userBodyInfo;
        this.role = role;
    }

    public static User of(UserId userId, UserAccountInfo userInfo, UserBodyInfo userBodyInfo, UserRole role) {
        return new User(
                userId,
                userInfo,
                userBodyInfo,
                role
        );
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public UserAccountInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserAccountInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserBodyInfo getUserBodyInfo() {
        return userBodyInfo;
    }

    public void setUserBodyInfo(UserBodyInfo userBodyInfo) {
        this.userBodyInfo = userBodyInfo;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
