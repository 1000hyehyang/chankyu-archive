package com.imchankyu.news.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.service.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * NewsArticleController - 수집된 뉴스 기사들을 제공하는 REST API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/news")
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    @Autowired
    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsArticle>>> getAllArticles() {
        List<NewsArticle> articles = newsArticleService.getAllArticles();
        return ResponseEntity.ok(new ApiResponse<>(true, "News articles retrieved successfully", articles));
    }
}
