package com.imchankyu.user.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.user.dto.LoginRequest;
import com.imchankyu.user.dto.LoginResponse;
import com.imchankyu.user.service.AuthService;
import com.imchankyu.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 로그인/토큰 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * [POST] /api/auth/login
     * 로그인 API - 이메일/비밀번호 입력 시 Access + Refresh Token 발급
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        // 로그인 처리
        LoginResponse response = authService.login(loginRequest);

        // Refresh Token 별도 발급
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getEmail());
        authService.saveRefreshToken(loginRequest.getEmail(), refreshToken);

        // 응답 생성
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(
                true,
                "Login successful",
                new LoginResponse(response.getAccessToken(), refreshToken)
        );

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Refresh Token으로 Access Token 재발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");

        // 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("유효하지 않은 리프레시 토큰입니다.");
        }

        // 이메일 추출 → 실제론 DB 조회 후 권한 확인 추천
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        if (!authService.isValidRefreshToken(email, refreshToken)) {
            throw new BadCredentialsException("리프레시 토큰이 일치하지 않습니다.");
        }

        // 새 Access Token 발급
        String newAccessToken = jwtTokenProvider.createToken(email);

        // 응답 반환
        return ResponseEntity.ok(new ApiResponse<>(true, "Access Token 재발급 성공",
                new LoginResponse(newAccessToken, refreshToken)));
    }

    /**
     * [POST] /api/auth/logout
     * 로그아웃 API - Refresh Token 삭제
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        authService.deleteRefreshToken(email);

        return ResponseEntity.ok(new ApiResponse<>(true, "로그아웃 처리 완료", null));
    }
}
