package com.imchankyu.user.service;

import com.imchankyu.user.dto.RegisterRequest;
import com.imchankyu.user.dto.UserDto;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 등록 및 조회 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 회원가입 처리
     */
    @Transactional
    public User registerUser(RegisterRequest request) {
        // 중복 이메일 체크
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
                .nickname(request.getNickname())
                .role("USER")
                .build();

        return userRepository.save(user);
    }

    /**
     * 전체 사용자 조회 (테스트용 or 관리용)
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getEmail(), user.getNickname()))
                .collect(Collectors.toList());
    }
}
