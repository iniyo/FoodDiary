package com.service.fooddiary.interfaces.mapper;

import com.service.fooddiary.application.dto.UserServiceQuery;
import com.service.fooddiary.domain.user.model.entity.User;
import com.service.fooddiary.interfaces.dto.user.UserInfoRequestDto;
import com.service.fooddiary.interfaces.dto.user.UserInfoResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserServiceQuery reqToQuery(UserInfoRequestDto dto) {
        return new UserServiceQuery(
                dto.email(),
                dto.name(),
                dto.phoneNumber(),
                dto.all() != null && (dto.all())
        );
    }

    public UserInfoRequestDto serviceToDto(UserServiceQuery dto) {
        return new UserInfoRequestDto(
                dto.email(),
                dto.name(),
                dto.phoneNumber(),
                dto.all()
        );
    }

    public UserInfoResponseDto domainToRes(User user) {
        return new UserInfoResponseDto(
                user.getUserInfo().getEmail().email(),
                user.getUserInfo().getUserName().name(),
                user.getUserInfo().getPhoneNumber().phoneNumber()
        );
    }
}
