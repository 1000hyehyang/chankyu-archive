package com.imchankyu.record.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.record.entity.PlayerRecord;
import com.imchankyu.record.service.PlayerRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class PlayerRecordController {

    private final PlayerRecordService recordService;

    // 전체 시즌 기록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlayerRecord>>> getAllRecords() {
        List<PlayerRecord> records = recordService.getAllRecords();
        return ResponseEntity.ok(new ApiResponse<>(true, "모든 시즌 기록 조회 성공", records));
    }

    // 특정 연도 기록 조회 (예: /api/records/year/2024)
    @GetMapping("/year/{year}")
    public ResponseEntity<ApiResponse<PlayerRecord>> getRecordByYear(@PathVariable int year) {
        PlayerRecord record = recordService.getRecordByYear(year);
        return ResponseEntity.ok(new ApiResponse<>(true, year + "년 시즌 기록 조회 성공", record));
    }
}
