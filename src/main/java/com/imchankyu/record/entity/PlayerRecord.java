package com.imchankyu.record.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_record")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;

    private int gameCount;

    private double innings;

    private double era;

    private int wins;

    private int losses;

    private int save;

    private int strikeouts;

}
