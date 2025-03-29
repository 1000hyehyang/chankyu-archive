package com.imchankyu.record.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.record.dto.PlayerRecordDto;
import com.imchankyu.record.service.PlayerRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class PlayerRecordController {

    private final PlayerRecordService playerRecordService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlayerRecordDto>>> getAllRecords() {
        List<PlayerRecordDto> records = playerRecordService.getAllRecords();
        return ResponseEntity.ok(new ApiResponse<>(true, "모든 시즌 기록 조회 성공", records));
    }

    @GetMapping("/{year}")
    public ResponseEntity<ApiResponse<PlayerRecordDto>> getRecordByYear(@PathVariable int year) {
        PlayerRecordDto record = playerRecordService.getRecordByYear(year);
        return ResponseEntity.ok(new ApiResponse<>(true, year + " 시즌 기록 조회 성공", record));
    }
}
