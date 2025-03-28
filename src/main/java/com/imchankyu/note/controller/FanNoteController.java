package com.imchankyu.note.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.note.entity.FanNote;
import com.imchankyu.note.service.FanNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FanNoteController - 팬 감상 메모 관련 REST API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/notes")
public class FanNoteController {

    private final FanNoteService fanNoteService;

    @Autowired
    public FanNoteController(FanNoteService fanNoteService) {
        this.fanNoteService = fanNoteService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FanNote>> createFanNote(@RequestBody FanNote note) {
        FanNote savedNote = fanNoteService.createFanNote(note);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fan note created successfully", savedNote));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FanNote>>> getAllFanNotes() {
        List<FanNote> notes = fanNoteService.getAllFanNotes();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fan notes retrieved successfully", notes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FanNote>> getFanNoteById(@PathVariable Long id) {
        return fanNoteService.getFanNoteById(id)
                .map(note -> ResponseEntity.ok(new ApiResponse<>(true, "Fan note retrieved successfully", note)))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse<>(false, "Fan note not found", null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FanNote>> updateFanNote(@PathVariable Long id, @RequestBody FanNote note) {
        note.setId(id);
        FanNote updatedNote = fanNoteService.updateFanNote(note);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fan note updated successfully", updatedNote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFanNote(@PathVariable Long id) {
        fanNoteService.deleteFanNote(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fan note deleted successfully", null));
    }
}
