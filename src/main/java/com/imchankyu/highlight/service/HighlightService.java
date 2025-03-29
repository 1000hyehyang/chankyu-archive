package com.imchankyu.highlight.service;

import com.imchankyu.highlight.entity.Highlight;
import com.imchankyu.highlight.repository.HighlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;

    // 명장면 전체 조회
    public List<Highlight> getAllHighlights() {
        return highlightRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}

