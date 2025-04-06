package com.imchankyu.auth.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * RefreshToken 엔티티 - 사용자의 Refresh Token을 저장
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 500)
    private String token;

    public void updateToken(String token) {
        this.token = token;
    }
}
