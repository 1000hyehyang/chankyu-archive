package com.imchankyu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 기본 설정 클래스
 * 현재는 기본 인증 및 CSRF 보호 등 최소한의 보안 설정을 적용합니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (개발 단계에서 편의를 위해)
                .csrf(csrf -> csrf.disable())
                // 모든 요청에 대해 인증 없이 접근 허용 (필요시 URL별 권한 설정 추가)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // 기본 폼 로그인 설정 (개발 단계용)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
