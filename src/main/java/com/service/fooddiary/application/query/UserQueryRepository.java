package com.service.fooddiary.application.query;

import com.service.fooddiary.application.dto.UserServiceQuery;
import com.service.fooddiary.domain.user.model.entity.User;

import java.util.List;

public interface UserQueryRepository {
    List<User> findUsersByDynamicCondition(UserServiceQuery queryDto);
}
