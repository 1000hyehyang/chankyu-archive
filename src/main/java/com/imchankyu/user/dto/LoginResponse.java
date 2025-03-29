package com.imchankyu.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 성공 시 클라이언트에 반환할 DTO
 */
@Getter
@Setter
public class LoginResponse {
    private String accessToken;  // JWT Access Token
    private String refreshToken; // JWT Refresh Token

    // Access Token만 사용하는 경우
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    // Access Token과 Refresh Token 모두 전달하는 경우
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
