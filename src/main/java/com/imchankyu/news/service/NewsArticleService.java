package com.imchankyu.news.service;

import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * NewsArticleService - 뉴스 기사 관련 비즈니스 로직을 처리합니다.
 */
@Service
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;

    @Autowired
    public NewsArticleService(NewsArticleRepository newsArticleRepository) {
        this.newsArticleRepository = newsArticleRepository;
    }

    public NewsArticle saveArticle(NewsArticle article) {
        // 링크를 기준으로 중복 확인 후, 없으면 저장
        Optional<NewsArticle> existingArticle = newsArticleRepository.findByLink(article.getLink());
        return existingArticle.orElseGet(() -> newsArticleRepository.save(article));
    }

    public List<NewsArticle> getAllArticles() {
        return newsArticleRepository.findAll();
    }
}
