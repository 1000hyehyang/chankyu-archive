package com.imchankyu.cheer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheerMessageUpdateRequest {
    private String content;
    private String password;
}
