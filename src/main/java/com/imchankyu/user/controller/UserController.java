package com.imchankyu.user.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController - 사용자 관련 REST API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 생성자 주입
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자 등록 API
     * @param user 등록할 사용자 정보
     * @return ApiResponse 객체 (성공 메시지와 데이터 포함)
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", savedUser));
    }

    /**
     * 모든 사용자 조회 API
     * @return ApiResponse 객체 (성공 메시지와 사용자 리스트 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    // 추가적인 API (로그인, 상세조회, 수정, 삭제 등)는 필요에 따라 구현
}
