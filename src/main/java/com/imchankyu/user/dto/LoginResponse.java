package com.imchankyu.user.dto;

import lombok.Getter;

/**
 * 로그인 성공 시 반환되는 응답 DTO
 */
@Getter
public class LoginResponse {
    private final String accessToken;
    private final String refreshToken;

    // Access Token만 쓰는 경우
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
        this.refreshToken = null;
    }

    // 둘 다 쓰는 경우
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
