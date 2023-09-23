package com.workids.domain.nation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestNationJoinDto {

    private Long teacherNum;

    private String name;

    private String moneyName;

    private int taxRate;

    private String presidentName;

    private String code;

    private String school;
    private int grade;
    private int classRoom;

    private int payDay;
    private int state;
    private Long balance; // default 자산


    private String startDate; // 나라 시작일
    private String endDate; // 나라 종료일
}
