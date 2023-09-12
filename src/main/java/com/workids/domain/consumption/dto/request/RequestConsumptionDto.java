package com.workids.domain.consumption.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestConsumptionDto {
    private Long nationNum;
    private String content;
    private int amount;
    private Long consumptionNum;
}