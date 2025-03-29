package com.imchankyu.note.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FanNoteRequestDto {
    private String title;
    private String content;
    private LocalDate date;
}
