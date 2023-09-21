package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.entity.Citizen;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseNationCitizenDto {
    Long citizenNum; // PK
    int citizenNumber; // 학급번호
    String studentName; // 학생이름
    String birthDate; // 생년월일

    public static ResponseNationCitizenDto toDto(Citizen entity) {
        return ResponseNationCitizenDto.builder()
                .citizenNum(entity.getCitizenNum())
                .citizenNumber(entity.getCitizenNumber())
                .studentName(entity.getName())
                .birthDate(entity.getBirthDate())
                .build();
    }
}
