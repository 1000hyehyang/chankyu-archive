package com.imchankyu.common.exception;

import com.imchankyu.common.util.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 전역 예외 처리 핸들러
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. JPA 엔티티 미조회 (404)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("EntityNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleEntityExistsException(EntityExistsException ex) {
        log.warn("EntityExistsException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT) // 409
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    // 2. 인증 실패 - 로그인 오류 등 (401)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
        log.warn("BadCredentialsException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "아이디 또는 비밀번호가 잘못되었습니다.", null));
    }

    // 3. 잘못된 요청 파라미터 (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    // 4. @Valid 유효성 검증 실패 (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");

        log.warn("Validation failed: {}", errorMessage);
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, errorMessage, null));
    }

    // 5. 그 외 모든 예외 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "서버 내부 오류가 발생했습니다.", null));
    }
}