package com.service.fooddiary.domain.user.model.entity;

import com.service.fooddiary.domain.user.model.vo.*;

import java.time.LocalDate;

public class UserAccountInfo {

    private UserName userName;
    private Email email;
    private PhoneNumber phoneNumber;
    private LocalDate createdAt;
    private Token token;

    private UserAccountInfo() {}

    private UserAccountInfo(UserName userName, Email email, PhoneNumber phoneNumber, Token token, LocalDate createdAt) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.token = token;
    }

    public static UserAccountInfo of(UserName userName, Email email, PhoneNumber phoneNumber, Token token, LocalDate createdAt) {
        return new UserAccountInfo(
                userName,
                email,
                phoneNumber,
                token,
                createdAt
        );
    }

    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

}
