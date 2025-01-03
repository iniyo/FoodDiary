package com.service.fooddiary.infrastructure.persistence.repository;

import com.service.fooddiary.infrastructure.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUserInfoEntityEmail(String email);

    List<UserEntity> findByUserInfoEntityUserName(String name);
}
