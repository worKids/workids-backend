package com.workids.domain.nation.dto.request;

import com.workids.domain.nation.entity.NationStudent;
import com.workids.global.config.stateType.NationStateType;
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
    private Long nationNum;

    private String code; // 참여코드
    private int citizenNumber; // 학급번호


}
