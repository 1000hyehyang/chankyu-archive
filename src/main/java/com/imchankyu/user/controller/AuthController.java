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
            throw new BadCredentialsException("Invalid refresh token");
        }

        // 이메일 추출 → 실제론 DB 조회 후 권한 확인 추천
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        // 새 Access Token 발급
        String newAccessToken = jwtTokenProvider.createToken(email);

        // 응답 반환
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(
                true,
                "Token refreshed successfully",
                new LoginResponse(newAccessToken, refreshToken)
        );

        return ResponseEntity.ok(apiResponse);
    }
}
