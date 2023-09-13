package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTeacherNationListDto {
    /**
     * 생성된 나라 조회: 나라이름, 나라 PK, 국민 총 수
     */

    private Long nationNum;
    private String name;
    private int totalStudent;

    public static ResponseTeacherNationListDto of(Nation nation, int totalStudent){
        return ResponseTeacherNationListDto.builder()
                .nationNum(nation.getNationNum())
                .name(nation.getName())
                .totalStudent(totalStudent)
                .build();
    }

}
