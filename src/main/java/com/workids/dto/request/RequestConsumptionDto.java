package com.workids.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseConsumptionDto {
    private long consumptionNum;
    private String content;
    private int amount;
    private LocalDateTime updateDate;
}
