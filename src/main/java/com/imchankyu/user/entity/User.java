package com.imchankyu.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User 엔티티 - 회원 정보를 저장합니다.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // 기본키: 사용자 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일 (중복 검사 필수)
    @Column(nullable = false, unique = true)
    private String email;

    // 암호화된 비밀번호
    @Column(nullable = false)
    private String password;

    // 사용자 닉네임
    @Column(nullable = false)
    private String nickname;

    // 팬 레벨 (옵션 필드)
    private Integer fanLevel;

    // 역할: USER 또는 ADMIN 등
    @Column(nullable = false)
    private String role;
}
