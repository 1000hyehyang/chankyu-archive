package com.imchankyu.cheer.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.service.CheerMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CheerMessageController - 응원 메시지 CRUD API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/cheers")
public class CheerMessageController {

    private final CheerMessageService cheerMessageService;

    @Autowired
    public CheerMessageController(CheerMessageService cheerMessageService) {
        this.cheerMessageService = cheerMessageService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CheerMessage>> createCheerMessage(@RequestBody CheerMessage message) {
        CheerMessage savedMessage = cheerMessageService.createCheerMessage(message);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cheer message created successfully", savedMessage));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CheerMessage>>> getAllCheerMessages() {
        List<CheerMessage> messages = cheerMessageService.getAllCheerMessages();
        return ResponseEntity.ok(new ApiResponse<>(true, "Cheer messages retrieved successfully", messages));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CheerMessage>> getCheerMessageById(@PathVariable Long id) {
        return cheerMessageService.getCheerMessageById(id)
                .map(message -> ResponseEntity.ok(new ApiResponse<>(true, "Cheer message retrieved successfully", message)))
                .orElse(ResponseEntity.badRequest().body(new ApiResponse<>(false, "Cheer message not found", null)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CheerMessage>> updateCheerMessage(@PathVariable Long id, @RequestBody CheerMessage message) {
        message.setId(id);
        CheerMessage updatedMessage = cheerMessageService.updateCheerMessage(message);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cheer message updated successfully", updatedMessage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCheerMessage(@PathVariable Long id) {
        cheerMessageService.deleteCheerMessage(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cheer message deleted successfully", null));
    }
}
