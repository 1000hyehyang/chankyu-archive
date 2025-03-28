package com.imchankyu.highlight.repository;

import com.imchankyu.highlight.entity.Highlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * HighlightRepository - Highlight 엔티티에 대한 CRUD 작업을 수행합니다.
 */
@Repository
public interface HighlightRepository extends JpaRepository<Highlight, Long> {
}
