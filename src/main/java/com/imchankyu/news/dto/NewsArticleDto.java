package com.imchankyu.news.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsArticleDto {
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private String imageUrl;
    private String source;
}
