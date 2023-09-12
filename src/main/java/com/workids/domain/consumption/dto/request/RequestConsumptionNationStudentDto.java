package com.workids.domain.consumption.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestConsumptionNationStudentDto {

    private Long nationNum;
    private Long consumptionNationStudentNum;
    private Long consumptionNum;
    private Long nationStudentNum;
    private int citizenNumber;
    private int state; //소비 신청 상태
}
