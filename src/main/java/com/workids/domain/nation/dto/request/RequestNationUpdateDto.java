package com.workids.domain.nation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestNationUpdateDto {

    private Long nationNum; // 나라 PK
    private String name; // 나라명

    private String moneyName; // 화폐명

    private int taxRate; // 세율

    private String presidentName; // 대통령 명

    private int payDay; // 월급 지급일

    private String startDate; // 나라 시작일
    private String endDate; // 나라 종료일
}
