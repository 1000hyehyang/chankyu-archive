package com.imchankyu.highlight.service;

import com.imchankyu.highlight.dto.HighlightDto;
import com.imchankyu.highlight.entity.Highlight;
import com.imchankyu.highlight.repository.HighlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HighlightService {

    private final HighlightRepository highlightRepository;

    public List<HighlightDto> getAllHighlights() {
        return highlightRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(highlight -> HighlightDto.builder()
                        .title(highlight.getTitle())
                        .description(highlight.getDescription())
                        .mediaUrl(highlight.getMediaUrl())
                        .createdAt(highlight.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
