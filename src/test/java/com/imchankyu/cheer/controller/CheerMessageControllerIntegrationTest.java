package com.imchankyu.cheer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.cheer.entity.CheerMessage;
import com.imchankyu.cheer.repository.CheerMessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CheerMessageControllerIntegrationTest - 응원 메시지 모듈의 CRUD 기능을 테스트합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CheerMessageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CheerMessageRepository cheerMessageRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateAndGetCheerMessage() throws Exception {
        // 응원 메시지 테스트 객체 생성
        CheerMessage message = CheerMessage.builder()
                .nickname("CheerFan")
                .passwordHash("hashedpassword")
                .content("Go team!")
                .createdAt(LocalDateTime.now())
                .build();

        // POST 요청으로 응원 메시지 생성 테스트
        mockMvc.perform(post("/api/cheers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.nickname", is("CheerFan")));

        // GET 요청으로 전체 응원 메시지 조회 테스트
        mockMvc.perform(get("/api/cheers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(not(empty()))));
    }
}
