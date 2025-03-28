package com.imchankyu.highlight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Highlight 엔티티는 임찬규 선수의 명장면(이미지/동영상) 정보를 저장합니다.
 */
@Entity
@Table(name = "highlights")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Highlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 설명
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // 이미지나 동영상 URL
    @Column(nullable = false)
    private String mediaUrl;

    // 생성 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
