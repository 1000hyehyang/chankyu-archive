package com.imchankyu.user.service;

import com.imchankyu.user.entity.User;
import com.imchankyu.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserService - 사용자 관련 비즈니스 로직을 처리합니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    // 생성자 주입
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 새로운 사용자를 등록합니다.
     * @param user 등록할 사용자 정보
     * @return 저장된 사용자 엔티티
     */
    public User registerUser(User user) {
        // 비밀번호 암호화, 이메일 중복 체크 등의 로직 추가 가능
        return userRepository.save(user);
    }

    /**
     * 모든 사용자를 조회합니다.
     * @return 사용자 리스트
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 이메일을 통해 사용자를 조회합니다.
     * @param email 사용자 이메일
     * @return Optional User
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 사용자 ID로 사용자를 조회합니다.
     * @param id 사용자 ID
     * @return Optional User
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 사용자 정보를 업데이트합니다.
     * @param user 업데이트할 사용자 정보
     * @return 업데이트된 사용자 엔티티
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 사용자 삭제
     * @param id 삭제할 사용자 ID
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
