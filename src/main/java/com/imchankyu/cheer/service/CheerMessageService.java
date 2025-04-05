package com.imchankyu.cheer.service;

import com.imchankyu.cheer.dto.CheerMessageRequest;
import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.repository.CheerMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheerMessageService {

    private final CheerMessageRepository cheerMessageRepository;

    public CheerMessage create(CheerMessageRequest request) {
        CheerMessage message = CheerMessage.builder()
                .nickname(request.getNickname())
                .content(request.getContent())
                .build();
        return cheerMessageRepository.save(message);
    }

    public Page<CheerMessage> findAllPaged(Pageable pageable) {
        return cheerMessageRepository.findAll(pageable);
    }
}
