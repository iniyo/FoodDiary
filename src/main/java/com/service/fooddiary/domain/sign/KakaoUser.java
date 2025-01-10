package com.service.fooddiary.domain.sign;

import lombok.Getter;

/**
 * 카카오로부터 가져오는 사용자 프로필 정보를 표현하는 Domain Model.
 */
@Getter
public class KakaoUser {
    private final Long kakaoId;    // 카카오 회원번호
    private final String nickname; // 별명
    private final String email;    // 이메일 (동의항목에 따라 null 일 수 있음)
    private final String profileImageUrl; // 프로필 이미지 URL (동의항목에 따라 null 일 수 있음)

    public KakaoUser(Long kakaoId, String nickname, String email, String profileImageUrl) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }
}
