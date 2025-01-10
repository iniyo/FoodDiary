package com.service.fooddiary.domain.user.repository;

import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.domain.user.model.vo.Email;
import com.service.fooddiary.domain.user.model.vo.UserName;

import java.util.List;

public interface UserRepository {
    List<User> findByUserName(UserName name);

    List<User> findAll();

    User findByUserEmail(Email email);
    void save(User user);
}
