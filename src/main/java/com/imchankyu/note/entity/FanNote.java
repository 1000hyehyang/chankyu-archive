package com.imchankyu.note.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * FanNote 엔티티는 팬들이 작성하는 감상 메모를 저장합니다.
 */
@Entity
@Table(name = "fan_notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FanNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Note 작성자 (User 엔티티와의 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private com.imchankyu.user.entity.User user;

    // 작성 날짜
    @Column(nullable = false)
    private LocalDate date;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
