package com.imchankyu.highlight.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HighlightDto {
    private String title;
    private String description;
    private String mediaUrl;
    private LocalDateTime createdAt;
}
