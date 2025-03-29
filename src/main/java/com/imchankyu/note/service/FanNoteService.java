package com.imchankyu.note.service;

import com.imchankyu.note.dto.FanNoteDto;
import com.imchankyu.note.dto.FanNoteRequestDto;
import com.imchankyu.note.entity.FanNote;
import com.imchankyu.note.repository.FanNoteRepository;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FanNoteService {

    private final FanNoteRepository fanNoteRepository;
    private final UserRepository userRepository;

    public FanNoteDto createNote(Long userId, FanNoteRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        FanNote note = FanNote.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(dto.getDate())
                .user(user)
                .build();

        return convertToDto(fanNoteRepository.save(note));
    }

    public List<FanNoteDto> getUserNotes(Long userId) {
        return fanNoteRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public FanNoteDto getNoteById(Long id, Long userId) {
        FanNote note = fanNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("메모를 찾을 수 없습니다."));
        if (!note.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 메모만 조회할 수 있습니다.");
        }
        return convertToDto(note);
    }

    public FanNoteDto updateNote(Long id, Long userId, FanNoteRequestDto dto) {
        FanNote note = fanNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("메모를 찾을 수 없습니다."));
        if (!note.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 메모만 수정할 수 있습니다.");
        }

        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setDate(dto.getDate());

        return convertToDto(fanNoteRepository.save(note));
    }

    public void deleteNote(Long id, Long userId) {
        FanNote note = fanNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("메모를 찾을 수 없습니다."));
        if (!note.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 메모만 삭제할 수 있습니다.");
        }
        fanNoteRepository.delete(note);
    }

    private FanNoteDto convertToDto(FanNote note) {
        return FanNoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .date(note.getDate())
                .userId(note.getUser().getId())
                .build();
    }
}
