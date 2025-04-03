package com.imchankyu.news.scheduler;

import com.imchankyu.news.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsScheduler {

    private final NewsArticleService newsArticleService;

    @Scheduled(cron = "0 0 3 * * *") // 매일 3시 정각 실행
    public void fetchAndSaveNews() {
        try {
            newsArticleService.saveDailyNews("임찬규");
        } catch (Exception e) {
            log.error("❌ 뉴스 수집 중 오류 발생", e);
        }
    }
}
