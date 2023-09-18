package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.entity.NationStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseNationStudentDto {
    private Long nationStudentNum;

    public static ResponseNationStudentDto toDto(NationStudent student) {
        return ResponseNationStudentDto.builder()
                .nationStudentNum(student.getNationStudentNum())
                .build();
    }
}
