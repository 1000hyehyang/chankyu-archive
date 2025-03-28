package com.imchankyu.highlight.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.highlight.entity.Highlight;
import com.imchankyu.highlight.repository.HighlightRepository;
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
 * HighlightControllerIntegrationTest - 명장면 모듈의 CRUD 기능을 테스트합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HighlightControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HighlightRepository highlightRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateAndGetHighlight() throws Exception {
        // 명장면 테스트 객체 생성
        Highlight highlight = Highlight.builder()
                .title("Amazing Strike")
                .description("A breathtaking strike in the game!")
                .mediaUrl("http://example.com/strike.jpg")
                .createdAt(LocalDateTime.now())
                .build();

        // POST 요청으로 명장면 생성 테스트
        mockMvc.perform(post("/api/highlights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(highlight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.title", is("Amazing Strike")));

        // GET 요청으로 전체 명장면 조회 테스트
        mockMvc.perform(get("/api/highlights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(not(empty()))));
    }
}
