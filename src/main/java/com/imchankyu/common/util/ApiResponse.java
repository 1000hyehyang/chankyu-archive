package com.imchankyu.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ApiResponse - 모든 API 응답에 공통으로 사용할 응답 객체.
 * 성공 여부, 메시지, 그리고 실제 데이터(payload)를 포함합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
}
