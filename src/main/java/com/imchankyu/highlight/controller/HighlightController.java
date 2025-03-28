package com.imchankyu.highlight.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.highlight.entity.Highlight;
import com.imchankyu.highlight.service.HighlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HighlightController - 명장면 CRUD API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/highlights")
public class HighlightController {

    private final HighlightService highlightService;

    @Autowired
    public HighlightController(HighlightService highlightService) {
        this.highlightService = highlightService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Highlight>> createHighlight(@RequestBody Highlight highlight) {
        Highlight savedHighlight = highlightService.createHighlight(highlight);
        return ResponseEntity.ok(new ApiResponse<>(true, "Highlight created successfully", savedHighlight));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Highlight>>> getAllHighlights() {
        List<Highlight> highlights = highlightService.getAllHighlights();
        return ResponseEntity.ok(new ApiResponse<>(true, "Highlights retrieved successfully", highlights));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Highlight>> getHighlightById(@PathVariable Long id) {
        return highlightService.getHighlightById(id)
                .map(highlight -> ResponseEntity.ok(new ApiResponse<>(true, "Highlight retrieved successfully", highlight)))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse<>(false, "Highlight not found", null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Highlight>> updateHighlight(@PathVariable Long id, @RequestBody Highlight highlight) {
        highlight.setId(id);
        Highlight updatedHighlight = highlightService.updateHighlight(highlight);
        return ResponseEntity.ok(new ApiResponse<>(true, "Highlight updated successfully", updatedHighlight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHighlight(@PathVariable Long id) {
        highlightService.deleteHighlight(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Highlight deleted successfully", null));
    }
}
