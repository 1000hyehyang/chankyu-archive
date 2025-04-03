package com.imchankyu.news.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.news.dto.NewsArticleDto;
import com.imchankyu.news.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsArticleController {
    private final NewsArticleService newsArticleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsArticleDto>>> getAllNews() {
        List<NewsArticleDto> articles = newsArticleService.getAllNews();
        return ResponseEntity.ok(ApiResponse.success(articles));
    }
}
