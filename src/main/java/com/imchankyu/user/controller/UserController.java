package com.imchankyu.user.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.user.dto.RegisterRequest;
import com.imchankyu.user.dto.UserDto;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 회원가입, 사용자 조회용 컨트롤러
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody RegisterRequest request) {
        User saved = userService.registerUser(request);
        UserDto userDto = new UserDto(saved.getId(), saved.getEmail(), saved.getNickname());
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", userDto));
    }

    /**
     * 전체 사용자 리스트 조회 (관리용)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }
}
