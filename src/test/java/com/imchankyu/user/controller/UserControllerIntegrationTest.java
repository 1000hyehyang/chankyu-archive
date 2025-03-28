package com.imchankyu.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
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
 * UserControllerIntegrationTest - 사용자 관리 모듈의 통합 테스트 클래스.
 * MockMvc를 사용하여 REST API 엔드포인트를 테스트합니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    // JSON 직렬화에 사용
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 사용자 등록 API 테스트
     */
    @Test
    public void testRegisterUser() throws Exception {
        // 테스트용 사용자 객체 생성 (비밀번호 암호화 로직이 없다면 그대로 저장)
        User user = User.builder()
                .email("test@example.com")
                .password("password123")
                .nickname("TestUser")
                .role("USER")
                .build();

        // POST 요청으로 사용자 등록 엔드포인트 호출
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.email", is("test@example.com")));

        // 등록 후 사용자 수가 0보다 큰지 확인 (간단한 검증)
        long count = userRepository.count();
        assert(count > 0);
    }

    /**
     * 전체 사용자 조회 API 테스트
     */
    @Test
    public void testGetAllUsers() throws Exception {
        // GET 요청으로 사용자 전체 리스트를 조회
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(not(empty()))));
    }
}