package com.imchankyu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 사용자 정보를 외부에 노출할 때 사용할 안전한 DTO
 */
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String nickname;
}
