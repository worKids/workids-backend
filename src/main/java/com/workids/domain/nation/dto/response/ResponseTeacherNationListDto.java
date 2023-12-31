package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.entity.Nation;
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
     * 생성된 나라 조회: 나라 PK, 나라 이름, 국민 총 수
     */

    private Long nationNum;
    private String name;
    private int totalStudent;
    private String moneyName;
    public static ResponseTeacherNationListDto of(Nation nation, int totalStudent){
        return ResponseTeacherNationListDto.builder()
                .nationNum(nation.getNationNum())
                .name(nation.getName())
                .totalStudent(totalStudent)
                .moneyName(nation.getMoneyName())
                .build();
    }

}
