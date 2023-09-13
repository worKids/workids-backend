package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.entity.NationStudent;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStudentNationListDto {
    /**
     * 생성된 나라 조회: 나라이름, 나라 PK, 국민 총 수
     */

    private Long nationNum;
    private String name;
    private int totalStudent;

    public static ResponseStudentNationListDto of(NationStudent nationstudent, int totalStudent){
        return ResponseStudentNationListDto.builder()
                .nationNum(nationstudent.getNation().getNationNum())
                .name(nationstudent.getStudentName())
                .totalStudent(totalStudent)
                .build();
    }

}
