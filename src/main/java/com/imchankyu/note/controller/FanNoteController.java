package com.imchankyu.note.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.note.dto.FanNoteDto;
import com.imchankyu.note.dto.FanNoteRequestDto;
import com.imchankyu.note.service.FanNoteService;
import com.imchankyu.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class FanNoteController {

    private final FanNoteService fanNoteService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<ApiResponse<FanNoteDto>> createNote(@RequestBody FanNoteRequestDto dto, Principal principal) {
        Long userId = jwtTokenProvider.getUserIdFromPrincipal(principal);
        FanNoteDto created = fanNoteService.createNote(userId, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "메모 작성 완료", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FanNoteDto>>> getMyNotes(Principal principal) {
        Long userId = jwtTokenProvider.getUserIdFromPrincipal(principal);
        List<FanNoteDto> notes = fanNoteService.getUserNotes(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "내 메모 조회 성공", notes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FanNoteDto>> getNote(@PathVariable Long id, Principal principal) {
        Long userId = jwtTokenProvider.getUserIdFromPrincipal(principal);
        FanNoteDto note = fanNoteService.getNoteById(id, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "단일 메모 조회 성공", note));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FanNoteDto>> updateNote(@PathVariable Long id, @RequestBody FanNoteRequestDto dto, Principal principal) {
        Long userId = jwtTokenProvider.getUserIdFromPrincipal(principal);
        FanNoteDto updated = fanNoteService.updateNote(id, userId, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "메모 수정 완료", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable Long id, Principal principal) {
        Long userId = jwtTokenProvider.getUserIdFromPrincipal(principal);
        fanNoteService.deleteNote(id, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "메모 삭제 완료", null));
    }
}
