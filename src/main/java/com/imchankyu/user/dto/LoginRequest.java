package com.imchankyu.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 시 사용되는 DTO
 */
@Getter
@Setter
public class LoginRequest {
    private String email;      // 사용자의 이메일
    private String password;   // 사용자의 비밀번호 (암호화 전)

    // 기본 생성자
    public LoginRequest() {}

    // 두 인자를 받는 생성자 추가
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
