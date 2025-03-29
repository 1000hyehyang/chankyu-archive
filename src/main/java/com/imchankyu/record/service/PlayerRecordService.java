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

    // 전체 기록 조회
    public List<PlayerRecord> getAllRecords() {
        return playerRecordRepository.findAll();
    }

    // 연도별 기록 조회
    public PlayerRecord getRecordByYear(int year) {
        return playerRecordRepository.findByYear(year)
                .orElseThrow(() -> new EntityNotFoundException(year + "년 시즌 기록이 존재하지 않습니다."));
    }
}
