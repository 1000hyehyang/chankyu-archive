package com.imchankyu.news.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.imchankyu.news.dto.NewsArticleDto;
import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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

    /**
     * 뉴스 기사 저장 + 중복 방지 + 이미지 썸네일 추가 + 임찬규 필터링
     */
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void fetchAndSaveNewsArticles() {
        String query = URLEncoder.encode("임찬규", StandardCharsets.UTF_8);
        String url = newsApiUrl + "?query=" + query + "&display=10&sort=date";

        JsonNode response = webClient.get()
                .uri(url)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (response == null || response.get("items") == null) {
            log.warn("네이버 뉴스 응답 없음 또는 오류 발생");
            return;
        }

        for (JsonNode item : response.get("items")) {
            String title = item.get("title").asText().replaceAll("<.*?>", "");
            String description = item.get("description").asText().replaceAll("<.*?>", "");

            // 필터링: 제목/본문에 "임찬규" 포함 안되면 저장하지 않음
            if (!title.contains("임찬규") && !description.contains("임찬규")) {
                continue;
            }

            String link = item.get("link").asText();
            String pubDate = item.get("pubDate").asText();
            String source = item.has("originallink") ? item.get("originallink").asText() : link;

            Optional<NewsArticle> existing = newsArticleRepository.findByLink(link);
            if (existing.isPresent()) continue;

            String imageUrl = fetchImageUrl(title);

            NewsArticle article = NewsArticle.builder()
                    .title(title)
                    .link(link)
                    .description(description)
                    .pubDate(pubDate)
                    .source(source)
                    .imageUrl(imageUrl)
                    .fetchedAt(LocalDateTime.now())
                    .build();

            newsArticleRepository.save(article);
            log.info("뉴스 기사 저장됨: {}", title);
        }
    }

    /**
     * 네이버 이미지 검색 API를 통해 썸네일 추출
     */
    private String fetchImageUrl(String query) {
        try {
            // 고정 키워드 "임찬규"로 이미지 검색
            String encoded = URLEncoder.encode("임찬규", StandardCharsets.UTF_8);
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

    /**
     * DTO 형태로 반환
     */
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

    public void saveArticle(NewsArticle article) {
        newsArticleRepository.save(article);
    }
}
