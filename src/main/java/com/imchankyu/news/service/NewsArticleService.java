package com.imchankyu.news.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.imchankyu.news.dto.NewsArticleDto;
import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsArticleService {

    private final NewsArticleRepository newsArticleRepository;

    @Value("${naver.api.url}")
    private String newsApiUrl;

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    @Value("${naver.api.clientId}")
    private String imageClientId;

    @Value("${naver.api.clientSecret}")
    private String imageClientSecret;

    private final WebClient webClient = WebClient.create();

    public String fetchImageUrl(String query) {
        try {
            String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String imageApiUrl = "https://openapi.naver.com/v1/search/image?query=" + encoded + "&display=1";

            JsonNode imageResponse = webClient.get()
                    .uri(imageApiUrl)
                    .header("X-Naver-Client-Id", imageClientId)
                    .header("X-Naver-Client-Secret", imageClientSecret)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            JsonNode items = imageResponse.get("items");
            if (items != null && items.size() > 0) {
                return items.get(0).get("link").asText(); // 썸네일 이미지 URL
            }

        } catch (Exception e) {
            log.warn("이미지 검색 실패: {}", e.getMessage());
        }
        return null;
    }

    public void saveArticle(NewsArticle article) {
        // 중복 방지
        Optional<NewsArticle> existing = newsArticleRepository.findByLink(article.getLink());
        if (existing.isPresent()) return;

        // 이미지 썸네일 가져오기
        String imageUrl = fetchImageUrl(article.getTitle());
        article.setImageUrl(imageUrl);

        article.setFetchedAt(LocalDateTime.now());
        newsArticleRepository.save(article);
        log.info("뉴스 기사 저장 완료: {}", article.getTitle());
    }

    public List<NewsArticleDto> getAllArticles() {
        return newsArticleRepository.findAll().stream()
                .map(article -> NewsArticleDto.builder()
                        .title(article.getTitle())
                        .link(article.getLink())
                        .pubDate(article.getPubDate())
                        .description(article.getDescription())
                        .source(article.getSource())
                        .imageUrl(article.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
