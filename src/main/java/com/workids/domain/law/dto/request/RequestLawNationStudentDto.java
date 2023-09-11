package com.workids.domain.law.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLawNationStudentDto {

    private Long nationNum;
    private Long lawNationStudentNum;
    private Long lawNum;
    private Long nationStudentNum;
    private int citizenNumber;
    private int penaltyCompleteState;

}