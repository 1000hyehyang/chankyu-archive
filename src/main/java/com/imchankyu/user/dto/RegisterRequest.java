package com.imchankyu.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 시 입력받을 필드들을 정의한 DTO
 */
@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
}
