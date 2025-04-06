package com.imchankyu.user.repository;

import com.imchankyu.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository - User 엔티티에 대한 CRUD 메서드를 제공합니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // 이메일 중복 체크
    boolean existsByNickname(String nickname); // 닉네임 중복 체크
    // 이메일로 사용자를 검색 (로그인 시 활용)
    Optional<User> findByEmail(String email);
}
