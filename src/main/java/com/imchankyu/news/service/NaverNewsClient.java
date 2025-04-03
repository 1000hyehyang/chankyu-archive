package com.imchankyu.news.service;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.news.dto.NewsArticleDto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class NaverNewsClient {
    @Value("${naver.api.url}")
    private String newsApiUrl;
    @Value("${naver.api.clientId}")
    private String clientId;
    @Value("${naver.api.clientSecret}")
    private String clientSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String defaultImageUrl = "https://www.tving.com/img/kbo/meta/kbo_meta_share.png";

    public List<NewsArticleDto> fetchNews(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = newsApiUrl + "?query=" + encodedQuery;

            HttpGet request = new HttpGet(url);
            request.addHeader("X-Naver-Client-Id", clientId);
            request.addHeader("X-Naver-Client-Secret", clientSecret);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(request)) {

                String responseBody = EntityUtils.toString(response.getEntity());
                Map<String, Object> resultMap = objectMapper.readValue(responseBody, Map.class);
                List<Map<String, Object>> items = (List<Map<String, Object>>) resultMap.get("items");

                if (items == null) return List.of();

                return items.stream().map(item -> {
                    String originallink = (String) item.get("originallink");
                    String image = extractImage(originallink);

                    return NewsArticleDto.builder()
                            .title((String) item.get("title"))
                            .link((String) item.get("link"))
                            .description((String) item.get("description"))
                            .pubDate((String) item.get("pubDate"))
                            .imageUrl(image)
                            .source((String) item.get("originallink"))
                            .build();
                }).toList();
            }
        } catch (Exception e) {
            throw new RuntimeException("네이버 뉴스 호출 실패: " + e.getMessage());
        }
    }

    private String extractImage(String articleUrl) {
        try {
            Document doc = Jsoup.connect(articleUrl).timeout(5000).get();
            Elements metaOgImage = doc.select("meta[property=og:image]");
            if (!metaOgImage.isEmpty()) {
                String content = metaOgImage.attr("content");
                if (content != null && !content.isEmpty()) return content;
            }
            Elements imgTags = doc.select("img");
            if (!imgTags.isEmpty()) {
                String src = imgTags.first().attr("src");
                if (src != null && !src.isEmpty()) return src;
            }
        } catch (Exception e) {
            System.out.println("이미지 추출 실패: " + e.getMessage());
        }
        return defaultImageUrl;
    }
}
