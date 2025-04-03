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

import java.util.*;
import java.util.stream.Collectors;

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

    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void fetchNews() {
        try {
            String query = "임찬규";
            String[] sortTypes = {"sim", "date"};

            Set<String> seenLinks = new HashSet<>();
            List<NewsArticle> uniqueArticles = new ArrayList<>();

            for (String sort : sortTypes) {
                String response = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("query", query)
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

                        String title = item.path("title").asText().replaceAll("<.*?>", "");
                        String description = item.path("description").asText().replaceAll("<.*?>", "");
                        String pubDate = item.path("pubDate").asText();
                        String source = item.has("originallink") ? item.get("originallink").asText() : link;

                        NewsArticle article = NewsArticle.builder()
                                .title(title)
                                .link(link)
                                .description(description)
                                .pubDate(pubDate)
                                .source(source)
                                .build();

                        uniqueArticles.add(article);
                    }
                }
            }

            // 최신순 정렬
            List<NewsArticle> sortedArticles = uniqueArticles.stream()
                    .sorted((a, b) -> b.getPubDate().compareTo(a.getPubDate()))
                    .collect(Collectors.toList());

            for (NewsArticle article : sortedArticles) {
                newsArticleService.saveArticle(article); // 썸네일 포함 저장
            }

            log.info("✅ 뉴스 기사 {}건 저장 완료", sortedArticles.size());

        } catch (Exception e) {
            log.error("❌ 뉴스 수집 중 오류 발생", e);
        }
    }
}
