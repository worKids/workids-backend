package com.workids.domain.nation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestNationStudentJoinDto {
    private Long studentNum;

    private String code; // 참여코드
    private int citizenNumber; // 학급번호


}
