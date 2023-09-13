package com.workids.domain.law.dto.response;

import com.workids.domain.law.entity.LawNationStudent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseLawNationStudentDto {
    //법-나라-학생 고유 번호
    private Long lawNationStudentNum;
    //학급 번호
    private int citizenNumber;
    //학생 이름
    private String studentName;
    //법 내용
    private String content;
    //벌금, 벌칙 type
    private int type;
    //벌금 금액
    private int fine;
    //벌칙 내용
    private String penalty;
    //벌칙 수행 여부
    private int penaltyCompleteState;
    //법 부여일
    private LocalDateTime createdDate;
    //법 부여 수정일(벌칙 수행일)
    private LocalDateTime updatedDate;

    public ResponseLawNationStudentDto toDto(LawNationStudent entity){
        return ResponseLawNationStudentDto.builder()
                .lawNationStudentNum(entity.getLawNationStudentNum())
                .citizenNumber(entity.getNationStudent().getCitizenNumber())
                .studentName(entity.getNationStudent().getStudentName())
                .content(entity.getLaw().getContent())
                .type(entity.getLaw().getType())
                .fine(entity.getLaw().getFine())
                .penalty(entity.getLaw().getPenalty())
                .penaltyCompleteState(entity.getPenaltyCompleteState())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

}
