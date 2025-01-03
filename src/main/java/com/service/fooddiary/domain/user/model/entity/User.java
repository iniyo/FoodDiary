package com.service.fooddiary.domain.user.model.entity;

import com.service.fooddiary.domain.common.AggregateRoot;
import com.service.fooddiary.domain.user.model.vo.UserId;

public class User extends AggregateRoot<User> {

    private UserId userId;
    private UserAccountInfo userInfo;
    private UserBodyInfo userBodyInfo;

    private User() {}

    private User(UserId userId, UserAccountInfo userInfo, UserBodyInfo userBodyInfo) {
        this.userId = userId;
        this.userInfo = userInfo;
        this.userBodyInfo = userBodyInfo;
    }

    public static User of(UserId userId, UserAccountInfo userInfo, UserBodyInfo userBodyInfo) {
        return new User(
                userId,
                userInfo,
                userBodyInfo
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
}
