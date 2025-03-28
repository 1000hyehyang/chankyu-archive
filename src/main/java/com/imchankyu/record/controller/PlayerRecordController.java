package com.imchankyu.record.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.record.entity.PlayerRecord;
import com.imchankyu.record.service.PlayerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PlayerRecordController - 선수 기록 CRUD API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/records")
public class PlayerRecordController {

    private final PlayerRecordService playerRecordService;

    @Autowired
    public PlayerRecordController(PlayerRecordService playerRecordService) {
        this.playerRecordService = playerRecordService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlayerRecord>> createRecord(@RequestBody PlayerRecord record) {
        PlayerRecord savedRecord = playerRecordService.createRecord(record);
        return ResponseEntity.ok(new ApiResponse<>(true, "Record created successfully", savedRecord));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlayerRecord>>> getAllRecords() {
        List<PlayerRecord> records = playerRecordService.getAllRecords();
        return ResponseEntity.ok(new ApiResponse<>(true, "Records retrieved successfully", records));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerRecord>> getRecordById(@PathVariable Long id) {
        return playerRecordService.getRecordById(id)
                .map(record -> ResponseEntity.ok(new ApiResponse<>(true, "Record retrieved successfully", record)))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse<>(false, "Record not found", null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlayerRecord>> updateRecord(@PathVariable Long id, @RequestBody PlayerRecord record) {
        record.setId(id);
        PlayerRecord updatedRecord = playerRecordService.updateRecord(record);
        return ResponseEntity.ok(new ApiResponse<>(true, "Record updated successfully", updatedRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRecord(@PathVariable Long id) {
        playerRecordService.deleteRecord(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Record deleted successfully", null));
    }
}
