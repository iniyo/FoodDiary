package com.service.fooddiary.interfaces.controller;

import com.service.fooddiary.application.usecase.UserUseCase;
import com.service.fooddiary.infrastructure.utils.ApiResult;
import com.service.fooddiary.interfaces.dto.user.UserInfoRequestDto;
import com.service.fooddiary.interfaces.dto.user.UserInfoResponseDto;
import com.service.fooddiary.interfaces.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    public UserController(UserUseCase userUseCase, UserMapper userMapper) {
        this.userUseCase = userUseCase;
        this.userMapper = userMapper;
    }

    @GetMapping("/find")
    public ResponseEntity<ApiResult<List<UserInfoResponseDto>>> AllUserForAttribute(@ModelAttribute UserInfoRequestDto dto) {
        List<UserInfoResponseDto> res = userUseCase.findUser(userMapper.reqToQuery(dto)).stream().map(userMapper::domainToRes).toList();
        return ResponseEntity.ok(ApiResult.ok(res));
    }
}
