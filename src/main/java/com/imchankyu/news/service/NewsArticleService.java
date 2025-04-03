package com.imchankyu.news.service;

import com.imchankyu.news.dto.NewsArticleDto;
import com.imchankyu.news.entity.NewsArticle;
import com.imchankyu.news.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsArticleService {
    private final NewsArticleRepository newsArticleRepository;
    private final NaverNewsClient naverNewsClient;

    public void saveDailyNews(String keyword) {
        List<NewsArticleDto> newsList = naverNewsClient.fetchNews(keyword);

        if (newsList.isEmpty()) return;

        // 수집한 뉴스 링크 목록 추출
        List<String> allLinks = newsList.stream()
                .map(NewsArticleDto::getLink)
                .collect(Collectors.toList());

        // DB에 이미 있는 링크들 한 번에 조회
        List<String> existingLinks = newsArticleRepository.findLinksByLinkIn(allLinks);

        // 중복되지 않은 기사만 저장
        List<NewsArticle> newArticles = newsList.stream()
                .filter(dto -> !existingLinks.contains(dto.getLink()))
                .map(dto -> NewsArticle.builder()
                        .title(dto.getTitle())
                        .link(dto.getLink())
                        .description(dto.getDescription())
                        .pubDate(dto.getPubDate())
                        .imageUrl(dto.getImageUrl())
                        .source(dto.getSource())
                        .fetchedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        newsArticleRepository.saveAll(newArticles); // ✅ saveAll로 일괄 저장
    }


    public List<NewsArticleDto> getAllNews() {
        return newsArticleRepository.findAll().stream().map(article ->
                NewsArticleDto.builder()
                        .title(article.getTitle())
                        .link(article.getLink())
                        .description(article.getDescription())
                        .pubDate(article.getPubDate())
                        .imageUrl(article.getImageUrl())
                        .source(article.getSource())
                        .build()
        ).toList();
    }
}
