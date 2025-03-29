package com.imchankyu.highlight.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.highlight.entity.Highlight;
import com.imchankyu.highlight.service.HighlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/highlights")
@RequiredArgsConstructor
public class HighlightController {

    private final HighlightService highlightService;

    // ✅ 리스트 조회 (로그인 불필요)
    @GetMapping
    public ResponseEntity<ApiResponse<List<Highlight>>> getAllHighlights() {
        List<Highlight> highlights = highlightService.getAllHighlights();
        return ResponseEntity.ok(new ApiResponse<>(true, "Highlights retrieved successfully", highlights));
    }
}
