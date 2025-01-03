package com.service.fooddiary.infrastructure.persistence.entity.user;

import com.service.fooddiary.domain.user.model.entity.UserBodyInfo;
import com.service.fooddiary.domain.user.model.enums.ActivityLevel;
import com.service.fooddiary.domain.user.model.enums.Gender;
import com.service.fooddiary.domain.user.model.vo.Age;
import com.service.fooddiary.domain.user.model.vo.Height;
import com.service.fooddiary.domain.user.model.vo.UserBodyInfoId;
import com.service.fooddiary.domain.user.model.vo.Weight;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "user_body_info")
public class UserBodyInfoEntity {

    @Id
    @GeneratedValue
    private UUID userBodyInfoId;
    @Enumerated(EnumType.STRING)  // ← 추가
    private Gender gender;
    private Long height;
    private int age;
    private Long weight;
    @Enumerated(EnumType.STRING)  // ← 추가
    private ActivityLevel activityLevel;
    private LocalDateTime lastDietEntry;
    private LocalDateTime lastWeightEntry;


    private UserBodyInfoEntity() {}

    public static UserBodyInfoEntity fromDomain() {
        return new UserBodyInfoEntity();
    }

    public UserBodyInfo toDomain() {
        return UserBodyInfo.of(
               new UserBodyInfoId(userBodyInfoId),
                gender,
                new Height(height),
                new Age(age),
                new Weight(weight),
                activityLevel,
                lastDietEntry,
                lastWeightEntry
        );
    }
}
