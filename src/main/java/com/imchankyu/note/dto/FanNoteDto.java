package com.imchankyu.note.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FanNoteDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate date;
    private Long userId;
}
