package com.imchankyu.cheer.service;

import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.repository.CheerMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CheerMessageService - 응원 메시지 관련 비즈니스 로직을 처리합니다.
 */
@Service
public class CheerMessageService {

    private final CheerMessageRepository cheerMessageRepository;

    @Autowired
    public CheerMessageService(CheerMessageRepository cheerMessageRepository) {
        this.cheerMessageRepository = cheerMessageRepository;
    }

    public CheerMessage createCheerMessage(CheerMessage message) {
        return cheerMessageRepository.save(message);
    }

    public List<CheerMessage> getAllCheerMessages() {
        return cheerMessageRepository.findAll();
    }

    public Optional<CheerMessage> getCheerMessageById(Long id) {
        return cheerMessageRepository.findById(id);
    }

    public CheerMessage updateCheerMessage(CheerMessage message) {
        return cheerMessageRepository.save(message);
    }

    public void deleteCheerMessage(Long id) {
        cheerMessageRepository.deleteById(id);
    }
}
