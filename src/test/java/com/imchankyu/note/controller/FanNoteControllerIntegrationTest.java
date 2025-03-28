package com.imchankyu.note.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.note.entity.FanNote;
import com.imchankyu.note.repository.FanNoteRepository;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * FanNoteControllerIntegrationTest - 팬 노트(감상 메모) 모듈의 CRUD 기능을 테스트합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class FanNoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FanNoteRepository fanNoteRepository;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateAndGetFanNote() throws Exception {
        // 먼저, 팬 노트를 작성할 사용자를 생성합니다.
        User user = User.builder()
                .email("fan@example.com")
                .password("fanpass")
                .nickname("FanUser")
                .role("USER")
                .build();
        user = userRepository.save(user);

        // 팬 노트 테스트 객체 생성
        FanNote note = FanNote.builder()
                .user(user)
                .date(LocalDate.now())
                .title("Amazing Game")
                .content("This game was amazing!")
                .build();

        // POST 요청으로 팬 노트 생성 테스트
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.title", is("Amazing Game")));

        // GET 요청으로 전체 팬 노트 조회 테스트
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(not(empty()))));
    }
}
