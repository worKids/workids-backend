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
    //나라 고유 번호
    private Long nationNum;
    //법-나라-학생 고유 번호
    private Long lawNationStudentNum;
    //법 항목 고유 번호
    private Long lawNum;
    //나라-학생 고유 번호
    private Long nationStudentNum;
    //학급 번호
    private int citizenNumber;
    //벌칙 수행 여부
    private int penaltyCompleteState;

}