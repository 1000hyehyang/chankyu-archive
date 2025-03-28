package com.imchankyu.record.repository;

import com.imchankyu.record.entity.PlayerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PlayerRecordRepository - PlayerRecord 엔티티에 대한 CRUD 메서드를 제공합니다.
 */
@Repository
public interface PlayerRecordRepository extends JpaRepository<PlayerRecord, Long> {
}
