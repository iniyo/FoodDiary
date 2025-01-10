package com.service.fooddiary.infrastructure.persistence.adapter;

import com.service.fooddiary.domain.common.exception.domain.NotFoundException;
import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.domain.user.model.vo.Email;
import com.service.fooddiary.domain.user.model.vo.UserName;
import com.service.fooddiary.domain.user.repository.UserRepository;
import com.service.fooddiary.infrastructure.persistence.entity.user.UserEntity;
import com.service.fooddiary.infrastructure.persistence.repository.JpaUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JpaUserAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public JpaUserAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<User> findByUserName(UserName name) {
        return jpaUserRepository.findByUserInfoEntityUserName(name.name()).stream().map(UserEntity::toDomain).toList();
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream().map(UserEntity::toDomain).toList();
    }

    @Override
    public User findByUserEmail(Email email) {
        return jpaUserRepository.findByUserInfoEntityEmail(email.email()).orElseThrow(NotFoundException::new).toDomain();
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = UserEntity.fromDomain(user);
        jpaUserRepository.save(userEntity);
    }
}
