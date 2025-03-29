package com.imchankyu.record.repository;

import com.imchankyu.record.entity.PlayerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, Long> {
    Optional<PlayerRecord> findByYear(int year); // 연도별 기록 조회
}
