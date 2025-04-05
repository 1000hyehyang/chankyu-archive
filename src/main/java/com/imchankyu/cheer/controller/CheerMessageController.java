package com.imchankyu.cheer.controller;

import com.imchankyu.cheer.dto.CheerMessageRequest;
import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.service.CheerMessageService;
import com.imchankyu.common.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cheers")
@RequiredArgsConstructor
public class CheerMessageController {

    private final CheerMessageService cheerMessageService;

    // POST: 응원 메시지 작성
    @PostMapping
    public ResponseEntity<ApiResponse<CheerMessage>> create(@RequestBody CheerMessageRequest request) {
        CheerMessage created = cheerMessageService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "응원 메시지 작성 완료", created));
    }

    // GET: 페이지네이션 방식 응원 메시지 조회 (프론트 요청과 매핑되는 부분)
    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<List<CheerMessage>>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(
                new ApiResponse<>(true, "응원 메시지 페이지 조회 완료",
                        cheerMessageService.findAllPaged(pageable).getContent())
        );
    }
}
