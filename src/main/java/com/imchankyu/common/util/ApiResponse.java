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

    // 성공 응답 생성용 정적 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "요청에 성공했습니다.", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // 실패 응답 생성용 정적 메서드
    public static <T> ApiResponse<T> failure(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
