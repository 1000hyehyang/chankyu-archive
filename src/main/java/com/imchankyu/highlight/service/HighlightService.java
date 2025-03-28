package com.imchankyu.highlight.service;

import com.imchankyu.highlight.entity.Highlight;
import com.imchankyu.highlight.repository.HighlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * HighlightService - 명장면 관련 비즈니스 로직을 처리합니다.
 */
@Service
public class HighlightService {

    private final HighlightRepository highlightRepository;

    @Autowired
    public HighlightService(HighlightRepository highlightRepository) {
        this.highlightRepository = highlightRepository;
    }

    public Highlight createHighlight(Highlight highlight) {
        return highlightRepository.save(highlight);
    }

    public List<Highlight> getAllHighlights() {
        return highlightRepository.findAll();
    }

    public Optional<Highlight> getHighlightById(Long id) {
        return highlightRepository.findById(id);
    }

    public Highlight updateHighlight(Highlight highlight) {
        return highlightRepository.save(highlight);
    }

    public void deleteHighlight(Long id) {
        highlightRepository.deleteById(id);
    }
}
