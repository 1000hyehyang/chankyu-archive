package com.imchankyu.highlight.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.highlight.dto.HighlightDto;
import com.imchankyu.highlight.service.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/highlights")
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<HighlightDto>>> getAllHighlights() {
        List<HighlightDto> highlights = highlightService.getAllHighlights();
        return ResponseEntity.ok(new ApiResponse<>(true, "명장면 목록 조회 성공", highlights));
    }
}
