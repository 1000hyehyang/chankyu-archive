package com.imchankyu.user.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.user.dto.LoginResponse;
import com.imchankyu.user.service.AuthService;
import com.imchankyu.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 로그인 관련 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 API - 이메일과 비밀번호를 받아 JWT 토큰 반환
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody Map<String, String> loginRequest) {
        // Map에서 이메일, 비밀번호 추출
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        // 로그인 시 Access Token 발급 (AuthService는 LoginResponse에 Access Token만 담아서 반환)
        LoginResponse response = authService.login(new com.imchankyu.user.dto.LoginRequest(email, password));
        // Refresh Token 발급
        String refreshToken = jwtTokenProvider.createRefreshToken(email);
        // Access Token과 Refresh Token을 함께 반환하는 LoginResponse 생성
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(true, "Login successful",
                new LoginResponse(response.getAccessToken(), refreshToken));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Refresh Token API - Refresh Token을 이용해 새로운 Access Token 발급
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        // DB 조회 등으로 사용자 역할을 확인할 수 있으나, 여기서는 간단하게 "USER"로 고정 처리
        String newAccessToken = jwtTokenProvider.createToken(email, "USER");
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(true, "Token refreshed successfully",
                new LoginResponse(newAccessToken, refreshToken));
        return ResponseEntity.ok(apiResponse);
    }
}
