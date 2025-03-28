package com.imchankyu.note.repository;

import com.imchankyu.note.entity.FanNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FanNoteRepository - FanNote 엔티티에 대한 CRUD 메서드를 제공합니다.
 */
@Repository
public interface FanNoteRepository extends JpaRepository<FanNote, Long> {

    // 특정 사용자에 대한 노트를 조회
    List<FanNote> findByUserId(Long userId);
}
