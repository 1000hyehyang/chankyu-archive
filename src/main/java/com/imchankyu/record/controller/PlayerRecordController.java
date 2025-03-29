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

    /**
     * 전체 시즌 기록 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<PlayerRecord>>> getAllRecords() {
        List<PlayerRecord> records = recordService.getAllRecords();
        return ResponseEntity.ok(new ApiResponse<>(true, "Records retrieved successfully", records));
    }

    /**
     * 단일 기록 조회 by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerRecord>> getRecordById(@PathVariable Long id) {
        PlayerRecord record = recordService.getRecordById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Record retrieved successfully", record));
    }
}
