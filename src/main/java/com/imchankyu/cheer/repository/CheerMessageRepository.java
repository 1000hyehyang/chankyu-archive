package com.imchankyu.cheer.repository;

import com.imchankyu.cheer.entity.CheerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheerMessageRepository extends JpaRepository<CheerMessage, Long> {
}
