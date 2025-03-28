package com.imchankyu.cheer.repository;

import com.imchankyu.cheer.entity.CheerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CheerMessageRepository - 응원 메시지에 대한 CRUD 작업을 수행합니다.
 */
@Repository
public interface CheerMessageRepository extends JpaRepository<CheerMessage, Long> {
}
