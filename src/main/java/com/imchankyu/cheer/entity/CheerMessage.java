package com.imchankyu.cheer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CheerMessage 엔티티는 팬들이 작성하는 응원 메시지를 저장합니다.
 * (익명 작성 및 수정/삭제를 위한 비밀번호 해시 포함)
 */
@Entity
@Table(name = "cheer_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheerMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 닉네임
    @Column(nullable = false)
    private String nickname;

    // 수정/삭제를 위한 비밀번호 해시
    @Column(nullable = false)
    private String passwordHash;

    // 메시지 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 작성 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
