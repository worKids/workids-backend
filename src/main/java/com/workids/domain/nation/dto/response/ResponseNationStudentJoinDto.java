package com.workids.domain.nation.dto.response;

import com.workids.global.config.stateType.NationStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseNationStudentJoinDto {
    private Long studentNum;
    private Long nationNum;
    private int citizenNumber;
    private int creditRating;
    private String studentName;
    private int state;

    public static ResponseNationStudentJoinDto of(ResponseNationStudentJoinDto dto) {
        return ResponseNationStudentJoinDto.builder()
                .studentNum(dto.getStudentNum())
                .nationNum(dto.getNationNum())
                .citizenNumber(dto.getCitizenNumber())
                .creditRating(50) // default 50
                .studentName(dto.getStudentName())
                .state(NationStateType.IN_NATION) // 가입완료: 1
                .build();
    }

}
