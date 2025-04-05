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

    @PostMapping
    public ResponseEntity<ApiResponse<CheerMessage>> create(@RequestBody CheerMessageRequest request) {
        CheerMessage created = cheerMessageService.create(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "응원 메시지 작성 완료", created));
    }

    @GetMapping("/paged")
    public ResponseEntity<ApiResponse<List<CheerMessage>>> findAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        List<CheerMessage> messages = cheerMessageService.findAllPaged(pageable).getContent();
        return ResponseEntity.ok(new ApiResponse<>(true, "페이지별 응원 메시지 조회 완료", messages));
    }
}
