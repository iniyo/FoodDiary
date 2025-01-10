package com.service.fooddiary.interfaces.controller;

import com.service.fooddiary.application.usecase.UserUseCase;
import com.service.fooddiary.infrastructure.utils.ApiResult;
import com.service.fooddiary.interfaces.dto.user.UserInfoRequestDto;
import com.service.fooddiary.interfaces.dto.user.UserInfoResponseDto;
import com.service.fooddiary.interfaces.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // 동적 조건으로 사용자 조회 (관리자 전용)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find")
    public ResponseEntity<ApiResult<List<UserInfoResponseDto>>> findUsersByCondition(@ModelAttribute UserInfoRequestDto dto) {
        List<UserInfoResponseDto> result = userUseCase
                .findUser(userMapper.reqToQuery(dto)) // UseCase 호출
                .stream()
                .map(userMapper::domainToRes) // Domain → Response DTO 변환
                .toList();

        return ResponseEntity.ok(ApiResult.ok(result));
    }


//    // 현재 사용자 정보 조회
//    @PostMapping("/me/kakao")
//    public ResponseEntity<ApiResult<UserInfoResponseDto>> kakaoSignIn(
//            @RequestBody KakaoUserResponseDto tokenRequest
//    ) {
//        // 전달된 AccessToken 확인
//        String accessToken = tokenRequest.getAccessToken();
//        log.info("카카오 AccessToken 수신: {}", accessToken);
//
//        /**
//         * 1) accessToken 유효성 검증 (필요하다면)
//         *    - 카카오 SDK나 Kakao OAuth 서버에 Validation 요청
//         *    - 또는 Spring Security에서 제공하는 JwtDecoder 활용
//         */
//        // 예시) kakaoJwtDecoder 등으로 토큰 검증.
//        // Kakao JWT는 'kid'(key id) 등을 통해 여러 공개키 중 하나로 검증합니다.
//        // 실제 검증 로직은 인프라 레벨에서 구현 가능.
//
//        /**
//         * 2) UserUseCase를 통해 내부 가입/로그인 로직 수행
//         *    - Kakao API를 통해 사용자의 email, profile 정보를 가져오거나
//         *      이미 저장된 사용자가 있다면 갱신 처리.
//         *    - 아래 예시는 단순히 이메일만 넣고 userUseCase의 processOAuth2Login을 가정.
//         *      실제로는 Kakao OpenAPI를 통해 프로필 조회 후, email/name 등을 가져오는 로직이 필요합니다.
//         */
//        // (가정) Kakao OpenAPI 호출하여 email, name, phoneNumber 등 추출
//        String kakaoEmail = "kakao_user@email.com";
//        String kakaoName = "카카오사용자";
//
//        // 가정으로 phoneNumber, fcmToken은 없는 경우 null
//        User user = userUseCase.processOAuth2Login(kakaoEmail, kakaoName, null, null);
//
//        // UserInfoResponseDto 생성
//        UserInfoResponseDto responseDto = userMapper.domainToRes(user);
//
//        // ResponseEntity로 반환
//        return ResponseEntity.ok(ApiResult.ok(responseDto));
//    }

}
