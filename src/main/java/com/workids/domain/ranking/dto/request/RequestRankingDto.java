package com.workids.domain.ranking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestRankingDto {
    
    //나라 고유 번호
    private Long nationNum;
    //나라-학생 고유 번호
    private Long nationStudentNum;
    //기간별 랭킹
    private char type;
}
