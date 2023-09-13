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
    //나라 고유 번호
    private Long nationNum;
    //소비-나라-학생 고유 번호
    private Long consumptionNationStudentNum;
    //소비 항목 고유 번호
    private Long consumptionNum;
    //나라-학생 고유 번호
    private Long nationStudentNum;
    //학급 번호
    private int citizenNumber;
    //소비 신청 상태
    private int state;
}
