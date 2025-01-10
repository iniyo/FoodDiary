package com.service.fooddiary.application.command;

import com.service.fooddiary.domain.user.model.vo.Email;
import com.service.fooddiary.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository; // 이메일 기반 사용자 조회를 위해 추가

    public UserSearchService(UserQueryRepository userSearchService, UserRepository userRepository) {
        this.userQueryRepository = userSearchService;
        this.userRepository = userRepository;
    }

    public List<User> execute(UserServiceQuery dto) {
        return userQueryRepository.findUsersByDynamicCondition(dto);
    }

    // 이메일로 사용자 조회
    public User getUserByEmail(String email) {
        return userRepository.findByUserEmail(new Email(email));
    }
}
