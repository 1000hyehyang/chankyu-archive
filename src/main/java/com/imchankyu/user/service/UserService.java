package com.imchankyu.user.service;

import com.imchankyu.user.dto.RegisterRequest;
import com.imchankyu.user.dto.UserDto;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 서비스 - 회원가입, 사용자 정보 조회 등을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     *
     * @param request 회원가입 요청 데이터 (이메일, 비밀번호, 닉네임)
     * @return 저장된 User 엔티티
     */
    public User registerUser(RegisterRequest request) {
        // 🔐 이메일 중복 여부 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("이미 사용 중인 이메일입니다.");
        }

        // 닉네임 중복 여부 확인
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new EntityExistsException("이미 사용 중인 닉네임입니다.");
        }

        // 🔐 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 🧱 User 엔티티 생성 (Builder 패턴 사용)
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();

        // 💾 DB에 저장
        return userRepository.save(user);
    }

    public boolean isNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 전체 사용자 리스트 조회 (관리자용 또는 테스트용)
     *
     * @return UserDto 리스트
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getNickname()
                ))
                .collect(Collectors.toList());
    }
}
