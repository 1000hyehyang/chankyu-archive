package com.imchankyu.record.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PlayerRecord 엔티티는 임찬규 선수의 시즌별 기록을 저장합니다.
 */
@Entity
@Table(name = "player_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 시즌 (예: 2025)
    @Column(nullable = false)
    private int year;

    // 승
    @Column(nullable = false)
    private int wins;

    // 패
    @Column(nullable = false)
    private int losses;

    // 평균 자책점 (ERA)
    @Column(nullable = false)
    private double era;

    // 이닝
    @Column(nullable = false)
    private double innings;

    // 삼진 수
    @Column(nullable = false)
    private int strikeouts;

    // 경기 수
    @Column(nullable = false)
    private int gameCount;
}
