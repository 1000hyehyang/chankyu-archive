package com.imchankyu.cheer.service;

import com.imchankyu.cheer.dto.CheerMessageRequest;
import com.imchankyu.cheer.dto.CheerMessageUpdateRequest;
import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.repository.CheerMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheerMessageService {

    private final CheerMessageRepository cheerMessageRepository;
    private final PasswordEncoder passwordEncoder;

    public CheerMessage create(CheerMessageRequest request) {
        CheerMessage message = CheerMessage.builder()
                .nickname(request.getNickname())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .content(request.getContent())
                .build();
        return cheerMessageRepository.save(message);
    }

    public List<CheerMessage> findAll() {
        return cheerMessageRepository.findAll();
    }

    public Optional<CheerMessage> findById(Long id) {
        return cheerMessageRepository.findById(id);
    }

    public CheerMessage update(Long id, CheerMessageUpdateRequest request) {
        CheerMessage message = cheerMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("응원 메시지를 찾을 수 없습니다."));

        if (!message.matchPassword(request.getPassword(), passwordEncoder)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        message.updateContent(request.getContent());
        return cheerMessageRepository.save(message);
    }

    public void delete(Long id, String password) {
        CheerMessage message = cheerMessageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("응원 메시지를 찾을 수 없습니다."));

        if (!message.matchPassword(password, passwordEncoder)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        cheerMessageRepository.delete(message);
    }
}
