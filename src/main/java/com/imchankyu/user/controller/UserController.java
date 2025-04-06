package com.imchankyu.user.controller;

import com.imchankyu.common.util.ApiResponse;
import com.imchankyu.user.dto.RegisterRequest;
import com.imchankyu.user.dto.UserDto;
import com.imchankyu.user.entity.User;
import com.imchankyu.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 회원가입 및 사용자 조회 컨트롤러
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * [POST] /api/users/register
     * 회원가입 API - 유효성 검증 포함
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody @Valid RegisterRequest request) {
        User saved = userService.registerUser(request);
        UserDto userDto = new UserDto(saved.getId(), saved.getEmail(), saved.getNickname());

        return ResponseEntity.ok(
                new ApiResponse<>(true, "회원가입이 완료되었습니다.", userDto)
        );
    }

    /**
     * [GET] /api/users
     * 전체 사용자 리스트 조회 (관리자용)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 목록 조회 성공", users));
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<ApiResponse<Boolean>> checkNickname(@RequestParam String nickname) {
        boolean isDuplicate = userService.isNicknameDuplicate(nickname);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "닉네임 중복 확인 완료", isDuplicate)
        );
    }
}
