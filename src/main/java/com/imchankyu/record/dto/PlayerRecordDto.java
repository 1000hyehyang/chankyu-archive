package com.imchankyu.record.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerRecordDto {
    private int year;
    private int gameCount;
    private double innings;
    private double era;
    private int wins;
    private int losses;
    private int save;
    private int strikeouts;
}
