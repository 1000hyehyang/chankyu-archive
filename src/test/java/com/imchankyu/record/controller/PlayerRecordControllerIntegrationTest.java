package com.imchankyu.record.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.record.entity.PlayerRecord;
import com.imchankyu.record.repository.PlayerRecordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * PlayerRecordControllerIntegrationTest - 선수 기록 모듈의 CRUD 기능을 테스트합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerRecordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRecordRepository playerRecordRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateAndGetPlayerRecord() throws Exception {
        // 선수 기록 테스트 객체 생성
        PlayerRecord record = PlayerRecord.builder()
                .year(2025)
                .wins(10)
                .losses(5)
                .era(3.5)
                .innings(150.0)
                .strikeouts(120)
                .gameCount(20)
                .build();

        // POST 요청으로 기록 생성 테스트
        mockMvc.perform(post("/api/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.year", is(2025)));

        // GET 요청으로 전체 기록 조회 테스트
        mockMvc.perform(get("/api/records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(not(empty()))));
    }
}
