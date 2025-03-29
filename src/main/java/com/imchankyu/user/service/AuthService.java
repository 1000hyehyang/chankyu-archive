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
 * 로그인 관련 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 처리 - 이메일로 사용자 조회 후 비밀번호 검증 후 JWT 토큰 생성
     */
    public LoginResponse login(LoginRequest request) {
        // 이메일로 사용자 찾기
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        // 사용자 역할 정보를 포함하여 JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        return new LoginResponse(token);
    }
}
