package com.imchankyu.cheer.controller;

import com.imchankyu.cheer.dto.CheerMessageRequest;
import com.imchankyu.cheer.dto.CheerMessageUpdateRequest;
import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.service.CheerMessageService;
import com.imchankyu.common.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cheers")
@RequiredArgsConstructor
public class CheerMessageController {

    private final CheerMessageService cheerMessageService;

    @PostMapping
    public ResponseEntity<ApiResponse<CheerMessage>> create(@RequestBody CheerMessageRequest request) {
        CheerMessage created = cheerMessageService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "응원 메시지 작성 완료", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CheerMessage>>> findAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "모든 응원 메시지 조회 완료", cheerMessageService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CheerMessage>> findById(@PathVariable Long id) {
        return cheerMessageService.findById(id)
                .map(msg -> ResponseEntity.ok(new ApiResponse<>(true, "응원 메시지 조회 완료", msg)))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse<>(false, "해당 메시지를 찾을 수 없습니다.", null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CheerMessage>> update(
            @PathVariable Long id,
            @RequestBody CheerMessageUpdateRequest request) {
        CheerMessage updated = cheerMessageService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "응원 메시지 수정 완료", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @RequestParam("password") String password) {
        cheerMessageService.delete(id, password);
        return ResponseEntity.ok(new ApiResponse<>(true, "응원 메시지 삭제 완료", null));
    }
}
