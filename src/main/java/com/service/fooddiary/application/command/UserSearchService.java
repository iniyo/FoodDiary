package com.service.fooddiary.application.command;

import com.service.fooddiary.infrastructure.utils.annotation.UseCaseService;
import com.service.fooddiary.application.dto.UserServiceQuery;
import com.service.fooddiary.application.query.UserQueryRepository;
import com.service.fooddiary.domain.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@UseCaseService
public class UserSearchService {

    private final UserQueryRepository userQueryRepository;

    public UserSearchService(UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    public List<User> execute(UserServiceQuery dto) {
        return userQueryRepository.findUsersByDynamicCondition(dto);
    }
}
