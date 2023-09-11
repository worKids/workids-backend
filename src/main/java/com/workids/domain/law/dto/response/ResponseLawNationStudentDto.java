package com.workids.domain.law.dto.response;

import com.workids.domain.law.entity.LawNationStudent;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseLawNationStudentDto {

    private Long lawNationStudentNum;
    private int citizenNumber;
    private String studentName;
    private String content;
    private int fine;
    private String penalty;
    private int penaltyCompleteState;

    public ResponseLawNationStudentDto toDto(LawNationStudent entity){
        return ResponseLawNationStudentDto.builder()
                .lawNationStudentNum(entity.getLawNationStudentNum())
                .citizenNumber(entity.getNationStudent().getCitizenNumber())
                .studentName(entity.getNationStudent().getStudentName())
                .content(entity.getLaw().getContent())
                .fine(entity.getLaw().getFine())
                .penalty(entity.getLaw().getPenalty())
                .penaltyCompleteState(entity.getPenaltyCompleteState())
                .build();
    }

}
