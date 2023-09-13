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
    //나라 고유 번호
    private Long nationNum;
    //소비 항목 내용
    private String content;
    //소비 항목 금액
    private int amount;
    //소비 항목 고유 번호
    private Long consumptionNum;
}