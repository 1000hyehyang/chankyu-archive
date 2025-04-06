package com.imchankyu.security;

import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService - 스프링 시큐리티 인증 시 사용자 정보 조회
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 이메일을 기준으로 사용자 정보 조회
     * @param email 사용자 이메일
     * @return UserDetails 구현 객체
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        // User 엔티티를 스프링 시큐리티 UserDetails 객체로 변환
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
