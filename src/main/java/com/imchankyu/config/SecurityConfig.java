package com.imchankyu.config;

import com.imchankyu.security.CustomUserDetailsService;
import com.imchankyu.security.JwtAuthenticationFilter;
import com.imchankyu.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 인증 관련 API, 회원가입
                        .requestMatchers("/api/auth/**", "/api/users/register").permitAll()

                        // 기록, 명장면, 뉴스 조회 - 모두 공개
                        .requestMatchers(HttpMethod.GET, "/api/records/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/highlights/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/news/**").permitAll()

                        // 응원글 읽기 + 작성 모두 공개
                        .requestMatchers(HttpMethod.GET, "/api/cheers/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/cheers/**").permitAll()

                        // 팬 노트는 모든 요청 인증 필요
                        .requestMatchers("/api/notes/**").authenticated()

                        // 기타 요청은 인증 없이 허용 (정적 자원 등)
                        .anyRequest().permitAll()
                )
                .userDetailsService(userDetailsService);

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173", // Vite dev server
                "https://chankyu-archive.vercel.app" // Vercel 배포 주소
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Content-Type, Authorization 등
        configuration.setAllowCredentials(true); // JWT나 세션 쿠키 사용 시 필요

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
