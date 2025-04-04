package com.imchankyu.record.service;

import com.imchankyu.record.dto.PlayerRecordDto;
import com.imchankyu.record.entity.PlayerRecord;
import com.imchankyu.record.repository.PlayerRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerRecordService {

    private final PlayerRecordRepository playerRecordRepository;

    public List<PlayerRecordDto> getAllRecords() {
        return playerRecordRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PlayerRecordDto getRecordByYear(int year) {
        PlayerRecord record = playerRecordRepository.findByYear(year)
                .orElseThrow(() -> new EntityNotFoundException(year + "년 시즌 기록이 존재하지 않습니다."));
        return convertToDto(record);
    }

    private PlayerRecordDto convertToDto(PlayerRecord record) {
        return PlayerRecordDto.builder()
                .year(record.getYear())
                .gameCount(record.getGameCount())
                .innings(record.getInnings())
                .era(record.getEra())
                .wins(record.getWins())
                .losses(record.getLosses())
                .save(record.getSave())
                .strikeouts(record.getStrikeouts())
                .build();
    }
}
