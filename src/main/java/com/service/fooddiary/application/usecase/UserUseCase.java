package com.service.fooddiary.application.usecase;


import com.service.fooddiary.application.command.UserSearchService;
import com.service.fooddiary.application.dto.UserServiceQuery;
import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.domain.user.model.entity.UserAccountInfo;
import com.service.fooddiary.domain.user.model.entity.UserBodyInfo;
import com.service.fooddiary.domain.user.model.enums.UserRole;
import com.service.fooddiary.domain.user.model.vo.*;
import com.service.fooddiary.domain.user.repository.UserRepository;
import com.service.fooddiary.infrastructure.utils.annotation.UseCase;

import java.util.List;
import java.util.Optional;

@UseCase
public class UserUseCase {

    private final UserSearchService userSearchService;

    public UserUseCase(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public List<User> findUser(UserServiceQuery dto) {
        return userSearchService.execute(dto);
    }

    // 이메일로 사용자 조회
    public User getUserByEmail(String email) {
        return userSearchService.getUserByEmail(email);
    }

}
