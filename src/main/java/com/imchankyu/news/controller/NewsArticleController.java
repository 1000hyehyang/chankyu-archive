package com.imchankyu.news.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.news.dto.NewsArticleDto;
import com.imchankyu.news.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsArticleDto>>> getAllArticles() {
        List<NewsArticleDto> articles = newsArticleService.getAllArticles();
        return ResponseEntity.ok(new ApiResponse<>(true, "뉴스 기사 목록 조회 성공", articles));
    }
}
