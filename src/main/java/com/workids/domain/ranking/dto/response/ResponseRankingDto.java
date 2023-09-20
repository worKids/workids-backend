package com.workids.domain.ranking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseRankingDto {
    //학생 이름
    private String studentName;
    //학급 번호
    private int citizenNumber;
    //결과
    private Long longResult;

}
