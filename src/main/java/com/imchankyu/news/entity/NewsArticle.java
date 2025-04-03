package com.imchankyu.news.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles", uniqueConstraints = {@UniqueConstraint(columnNames = {"link"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1024)
    private String link;

    @Column(nullable = false)
    private String pubDate;

    @Column(length = 1024)
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 512)
    private String source;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt;

    @PrePersist
    protected void onCreate() {
        if (fetchedAt == null) {
            fetchedAt = LocalDateTime.now();
        }
    }

}
