package com.imchankyu.record.service;

import com.imchankyu.record.entity.PlayerRecord;
import com.imchankyu.record.repository.PlayerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * PlayerRecordService - 선수 기록 관련 비즈니스 로직을 처리합니다.
 */
@Service
public class PlayerRecordService {

    private final PlayerRecordRepository playerRecordRepository;

    @Autowired
    public PlayerRecordService(PlayerRecordRepository playerRecordRepository) {
        this.playerRecordRepository = playerRecordRepository;
    }

    public PlayerRecord createRecord(PlayerRecord record) {
        return playerRecordRepository.save(record);
    }

    public List<PlayerRecord> getAllRecords() {
        return playerRecordRepository.findAll();
    }

    public Optional<PlayerRecord> getRecordById(Long id) {
        return playerRecordRepository.findById(id);
    }

    public PlayerRecord updateRecord(PlayerRecord record) {
        return playerRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        playerRecordRepository.deleteById(id);
    }
}