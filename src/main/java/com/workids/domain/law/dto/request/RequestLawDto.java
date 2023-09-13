package com.workids.domain.law.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLawDto {
    //나라 고유 번호
    private Long nationNum;
    //법 내용
    private String content;
    //벌금, 벌칙 type
    private int type;
    //벌금 금액
    private int fine;
    //벌칙 내용
    private String penalty;
    //법 항목 고유 번호
    private Long lawNum;

}
