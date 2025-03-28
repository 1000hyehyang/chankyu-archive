package com.imchankyu.news.repository;

import com.imchankyu.news.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * NewsArticleRepository - NewsArticle 엔티티에 대한 CRUD 작업을 수행합니다.
 */
@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    // 중복 기사 저장 방지를 위해 링크로 조회
    Optional<NewsArticle> findByLink(String link);
}
