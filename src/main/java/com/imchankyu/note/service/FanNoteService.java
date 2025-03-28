package com.imchankyu.note.service;

import com.imchankyu.note.entity.FanNote;
import com.imchankyu.note.repository.FanNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * FanNoteService - 감상 메모 관련 비즈니스 로직을 처리합니다.
 */
@Service
public class FanNoteService {

    private final FanNoteRepository fanNoteRepository;

    @Autowired
    public FanNoteService(FanNoteRepository fanNoteRepository) {
        this.fanNoteRepository = fanNoteRepository;
    }

    public FanNote createFanNote(FanNote note) {
        return fanNoteRepository.save(note);
    }

    public List<FanNote> getAllFanNotes() {
        return fanNoteRepository.findAll();
    }

    public Optional<FanNote> getFanNoteById(Long id) {
        return fanNoteRepository.findById(id);
    }

    public FanNote updateFanNote(FanNote note) {
        return fanNoteRepository.save(note);
    }

    public void deleteFanNote(Long id) {
        fanNoteRepository.deleteById(id);
    }

    public List<FanNote> getFanNotesByUserId(Long userId) {
        return fanNoteRepository.findByUserId(userId);
    }
}
