package com.service.fooddiary.domain.user.model.entity;

import com.service.fooddiary.domain.user.model.enums.ActivityLevel;
import com.service.fooddiary.domain.user.model.enums.Gender;
import com.service.fooddiary.domain.user.model.vo.*;

import java.time.LocalDateTime;

public class UserBodyInfo {

    private UserBodyInfoId userBodyInfoId;
    private Gender gender;
    private Height height;
    private Age age;
    private Weight weight;
    private ActivityLevel activityLevel;
    private LocalDateTime lastDietEntry;
    private LocalDateTime lastWeightEntry;

    private UserBodyInfo() {
    }

    private UserBodyInfo(UserBodyInfoId userBodyInfoId, Gender gender, Height height, Age age, Weight weight, ActivityLevel activityLevel, LocalDateTime lastDietEntry, LocalDateTime lastWeightEntry) {
        this.userBodyInfoId = userBodyInfoId;
        this.gender = gender;
        this.height = height;
        this.age = age;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.lastDietEntry = lastDietEntry;
        this.lastWeightEntry = lastWeightEntry;
    }

    public static UserBodyInfo of(UserBodyInfoId userBodyInfoId, Gender gender, Height height, Age age, Weight weight, ActivityLevel activityLevel, LocalDateTime lastDietEntry, LocalDateTime lastWeightEntry) {
        return new UserBodyInfo(
                userBodyInfoId,
                gender,
                height,
                age,
                weight,
                activityLevel,
                lastDietEntry,
                lastWeightEntry
        );
    }

    public static UserBodyInfo of() {
        return new UserBodyInfo(

        );
    }


    public UserBodyInfoId getUserBodyInfoId() {
        return userBodyInfoId;
    }

    public void setUserBodyInfoId(UserBodyInfoId userBodyInfoId) {
        this.userBodyInfoId = userBodyInfoId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public LocalDateTime getLastDietEntry() {
        return lastDietEntry;
    }

    public void setLastDietEntry(LocalDateTime lastDietEntry) {
        this.lastDietEntry = lastDietEntry;
    }

    public LocalDateTime getLastWeightEntry() {
        return lastWeightEntry;
    }

    public void setLastWeightEntry(LocalDateTime lastWeightEntry) {
        this.lastWeightEntry = lastWeightEntry;
    }
}
