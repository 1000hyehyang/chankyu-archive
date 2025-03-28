package com.imchankyu.news.scheduler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.service.NewsArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class NewsScheduler {

    private final NewsArticleService newsArticleService;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${naver.api.clientId}")
    private String clientId;

    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    public NewsScheduler(NewsArticleService newsArticleService,
                         @Value("${naver.api.url}") String naverApiUrl) {
        this.newsArticleService = newsArticleService;
        this.webClient = WebClient.builder()
                .baseUrl(naverApiUrl)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 1시간마다 네이버 뉴스 API를 호출하여 "임찬규" 관련 기사를 sim 정렬로 수집합니다.
     */
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void fetchNews() {
        try {
            String query = "임찬규";
            String[] sortTypes = {"sim", "date"}; // 관련도, 최신순 둘 다

            Set<String> seenLinks = new HashSet<>(); // 중복 제거용
            List<NewsArticle> uniqueArticles = new ArrayList<>();

            for (String sort : sortTypes) {
                String response = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("query", query) // URLEncoder 인코딩 생략
                                .queryParam("sort", sort)
                                .queryParam("display", 10)
                                .build())
                        .header("X-Naver-Client-Id", clientId)
                        .header("X-Naver-Client-Secret", clientSecret)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                JsonNode items = objectMapper.readTree(response).path("items");
                if (items.isArray()) {
                    for (JsonNode item : items) {
                        String link = item.path("link").asText();
                        if (seenLinks.contains(link)) continue;

                        seenLinks.add(link);

                        NewsArticle article = NewsArticle.builder()
                                .title(item.path("title").asText().replaceAll("<.*?>", ""))
                                .link(link)
                                .pubDate(item.path("pubDate").asText())
                                .description(item.path("description").asText().replaceAll("<.*?>", ""))
                                .source(item.path("originallink").asText())
                                .fetchedAt(LocalDateTime.now())
                                .build();

                        uniqueArticles.add(article);
                    }
                }
            }

            // 최신순 정렬
            uniqueArticles.sort((a, b) -> b.getPubDate().compareTo(a.getPubDate()));

            for (NewsArticle article : uniqueArticles) {
                newsArticleService.saveArticle(article);
            }

            log.info("임찬규 관련 뉴스 기사 {}건 저장 완료", uniqueArticles.size());

        } catch (Exception e) {
            log.error("임찬규 뉴스 수집 중 오류 발생", e);
        }
    }
}
