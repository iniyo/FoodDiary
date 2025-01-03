package com.service.fooddiary.application.usecase;


import com.service.fooddiary.application.command.UserSearchService;
import com.service.fooddiary.infrastructure.utils.annotation.UseCase;
import com.service.fooddiary.application.dto.UserServiceQuery;
import com.service.fooddiary.domain.user.model.entity.User;

import java.util.List;

@UseCase
public class UserUseCase {

    private final UserSearchService userSearchService;

    public UserUseCase(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public List<User> findUser(UserServiceQuery dto) {
        return userSearchService.execute(dto);
    }
}
