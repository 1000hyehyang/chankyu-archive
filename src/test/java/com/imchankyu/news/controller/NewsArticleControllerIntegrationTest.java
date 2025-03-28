package com.imchankyu.news.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.repository.NewsArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * NewsArticleControllerIntegrationTest - 뉴스 기사 모듈의 조회 기능을 테스트합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class NewsArticleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsArticleRepository newsArticleRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllNewsArticles() throws Exception {
        // 테스트용 뉴스 기사 객체 생성
        NewsArticle article = NewsArticle.builder()
                .title("News Title")
                .link("http://example.com/news1")
                .pubDate("2025-03-29")
                .description("News description")
                .source("Example Source")
                .fetchedAt(LocalDateTime.now())
                .build();

        newsArticleRepository.save(article);

        // GET 요청으로 전체 뉴스 기사 조회 테스트
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(not(empty()))));
    }
}
