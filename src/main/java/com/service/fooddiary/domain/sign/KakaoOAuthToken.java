package com.service.fooddiary.domain.sign;


import lombok.Getter;

/**
 * 카카오 OAuth 토큰 정보를 표현하는 Domain Model.
 */
@Getter
public class KakaoOAuthToken {
    private final String accessToken;
    private final String refreshToken;
    private final long expiresIn;
    private final long refreshTokenExpiresIn;
    private final String scope;

    public KakaoOAuthToken(String accessToken, String refreshToken, long expiresIn, long refreshTokenExpiresIn, String scope) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.scope = scope;
    }
}
