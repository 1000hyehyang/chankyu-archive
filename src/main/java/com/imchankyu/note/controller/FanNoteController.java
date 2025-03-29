package com.imchankyu.note.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.note.dto.FanNoteDto;
import com.imchankyu.note.dto.FanNoteRequestDto;
import com.imchankyu.note.service.FanNoteService;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserRepository userRepository;

    /**
     * Principal에서 userId 추출하는 헬퍼 메서드
     */
    private Long extractUserIdFromPrincipal(Principal principal) {
        String email = principal.getName(); // principal 자체에 이메일이 들어있음
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return user.getId();
    }

    /**
     * 팬 노트 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FanNoteDto>> createNote(@RequestBody FanNoteRequestDto dto, Principal principal) {
        Long userId = extractUserIdFromPrincipal(principal);
        FanNoteDto created = fanNoteService.createNote(userId, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "메모 작성 완료", created));
    }

    /**
     * 내 메모 전체 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<FanNoteDto>>> getMyNotes(Principal principal) {
        Long userId = extractUserIdFromPrincipal(principal);
        List<FanNoteDto> notes = fanNoteService.getUserNotes(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "내 메모 조회 성공", notes));
    }

    /**
     * 단일 메모 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FanNoteDto>> getNote(@PathVariable Long id, Principal principal) {
        Long userId = extractUserIdFromPrincipal(principal);
        FanNoteDto note = fanNoteService.getNoteById(id, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "단일 메모 조회 성공", note));
    }

    /**
     * 메모 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FanNoteDto>> updateNote(@PathVariable Long id, @RequestBody FanNoteRequestDto dto, Principal principal) {
        Long userId = extractUserIdFromPrincipal(principal);
        FanNoteDto updated = fanNoteService.updateNote(id, userId, dto);
        return ResponseEntity.ok(new ApiResponse<>(true, "메모 수정 완료", updated));
    }

    /**
     * 메모 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable Long id, Principal principal) {
        Long userId = extractUserIdFromPrincipal(principal);
        fanNoteService.deleteNote(id, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "메모 삭제 완료", null));
    }
}
