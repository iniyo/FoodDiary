//package com.service.fooddiary.application.command;
//
//import com.service.fooddiary.domain.common.exception.domain.NotFoundException;
//import com.service.fooddiary.domain.user.model.entity.User;
//import com.service.fooddiary.domain.user.model.enums.UserRole;
//import com.service.fooddiary.domain.user.model.vo.Email;
//import com.service.fooddiary.domain.user.repository.UserRepository;
//import com.service.fooddiary.infrastructure.utils.annotation.UseCaseService;
//
//@UseCaseService
//public class UserSignService {
//    private final UserRepository userRepository;
//
//    public UserSignService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public boolean hasAdminPermission(String email) {
//        User user = userRepository.findByUserEmail(new Email(email));
//        return user.getRole() == UserRole.ROLE_ADMIN;
//    }
//}
