package com.imchankyu.news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * NewsArticle 엔티티는 네이버 뉴스 API를 통해 수집된 임찬규 관련 기사 정보를 저장합니다.
 */
@Entity
@Table(name = "news_articles", uniqueConstraints = {@UniqueConstraint(columnNames = {"link"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기사 제목
    @Column(nullable = false)
    private String title;

    // 기사 링크 (길이 확장)
    @Column(nullable = false, length = 1024)
    private String link;

    // 기사 발행일 (문자열 형식)
    @Column(nullable = false)
    private String pubDate;

    @Column(length = 1024)
    private String imageUrl;  // 추가된 필드

    // 기사 설명 또는 요약
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    // 기사 출처 (길이 512로 확장)
    @Column(nullable = false, length = 512)
    private String source;

    // 데이터 수집 시간
    @Column(nullable = false)
    private LocalDateTime fetchedAt;
}
