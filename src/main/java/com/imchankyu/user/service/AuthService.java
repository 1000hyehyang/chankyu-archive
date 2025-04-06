package com.imchankyu.user.service;

import com.imchankyu.security.JwtTokenProvider;
import com.imchankyu.user.dto.LoginRequest;
import com.imchankyu.user.dto.LoginResponse;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 인증 서비스 - 로그인 처리 및 인증 관련 기능 제공
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 처리
     *
     * @param request 로그인 요청 (이메일, 비밀번호)
     * @return 로그인 성공 시 AccessToken 포함된 응답 DTO
     */
    public LoginResponse login(LoginRequest request) {
        // 🔍 사용자 조회 (이메일 기준)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("해당 이메일의 사용자를 찾을 수 없습니다."));

        // 🔐 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 🔑 JWT Access Token 발급 (이제 역할 없이 이메일만 사용)
        String token = jwtTokenProvider.createToken(user.getEmail());

        return new LoginResponse(token);
    }
}
