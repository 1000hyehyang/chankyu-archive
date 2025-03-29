package com.imchankyu.record.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerRecordDto {
    private int year;
    private int wins;
    private int losses;
    private double era;
    private double innings;
    private int strikeouts;
    private int gameCount;
}
