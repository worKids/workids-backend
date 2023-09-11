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
    private Long nationNum;
    private String content;
    private int type;
    private int fine;
    private String penalty;
    private Long lawNum; //수정할때 필요

}
