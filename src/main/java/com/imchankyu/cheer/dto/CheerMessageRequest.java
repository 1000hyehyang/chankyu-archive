package com.imchankyu.cheer.dto;

import lombok.Data;

@Data
public class CheerMessageRequest {
    private String nickname;
    private String content;
    private String password; // 현재 사용하지 않지만 필드는 두자
}
