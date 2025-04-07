package com.imchankyu.user.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.user.dto.LoginRequest;
import com.imchankyu.user.dto.LoginResponse;
import com.imchankyu.user.service.AuthService;
import com.imchankyu.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 API
     * accessToken은 body로, refreshToken은 HttpOnly 쿠키로 설정
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        LoginResponse responseDto = authService.login(loginRequest);

        // 🔑 Refresh Token 생성 및 저장
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getEmail());
        authService.saveRefreshToken(loginRequest.getEmail(), refreshToken);

        // ✅ refreshToken을 HttpOnly 쿠키에 설정
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7일
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // ✅ 응답 body에는 accessToken만 전달
        return ResponseEntity.ok(
                ApiResponse.success("로그인 성공", new LoginResponse(responseDto.getAccessToken(), null))
        );
    }

    /**
     * AccessToken 재발급 API
     * 요청의 HttpOnly 쿠키에서 refreshToken을 읽어 처리
     */
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("유효하지 않은 리프레시 토큰입니다.");
        }

        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        if (!authService.isValidRefreshToken(email, refreshToken)) {
            throw new BadCredentialsException("리프레시 토큰이 일치하지 않습니다.");
        }

        String newAccessToken = jwtTokenProvider.createToken(email);

        return ResponseEntity.ok(
                ApiResponse.success("Access Token 재발급 성공", new LoginResponse(newAccessToken, null))
        );
    }

    /**
     * 로그아웃 API
     * refreshToken 삭제 + 쿠키 만료 설정
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
            String email = jwtTokenProvider.getEmailFromToken(refreshToken);
            authService.deleteRefreshToken(email);
        }

        // ✅ refreshToken 쿠키 제거
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0) // 삭제
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.ok(ApiResponse.success("로그아웃 완료", null));
    }
}