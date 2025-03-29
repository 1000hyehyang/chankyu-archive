package com.imchankyu.record.service;

import com.imchankyu.record.entity.PlayerRecord;
import com.imchankyu.record.repository.PlayerRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerRecordService {

    private final PlayerRecordRepository playerRecordRepository;

    /**
     * 전체 시즌 기록 조회
     */
    public List<PlayerRecord> getAllRecords() {
        return playerRecordRepository.findAll();
    }

    /**
     * 단일 시즌 기록 조회 (ID 기준)
     */
    public PlayerRecord getRecordById(Long id) {
        return playerRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 기록이 존재하지 않습니다: " + id));
    }
}