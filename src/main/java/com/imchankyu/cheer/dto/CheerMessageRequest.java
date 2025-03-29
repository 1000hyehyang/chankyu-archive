package com.imchankyu.cheer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheerMessageRequest {
    private String nickname;
    private String password;
    private String content;
}
