package com.workids.domain.consumption.dto.response;

import com.workids.domain.consumption.entity.ConsumptionNationStudent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseConsumptionNationStudentDto {

    //소비-나라-학생 고유 번호
    private Long consumptionNationStudentNum;
    //학급 번호
    private int citizenNumber;
    //학생 이름
    private String studentName;
    //소비 항목 내용
    private String content;
    //소비 항목 금액
    private int amount;
    //소비 신청 상태
    private int state;
    //소비 신청일
    private LocalDateTime createdDate;
    //소비 신청 내역 수정일(승낙, 거절일)
    private LocalDateTime updatedDate;

    public ResponseConsumptionNationStudentDto toDto(ConsumptionNationStudent entity){
        return ResponseConsumptionNationStudentDto.builder()
                .consumptionNationStudentNum(entity.getConsumptionNationStudentNum())
                .citizenNumber(entity.getNationStudent().getCitizenNumber())
                .studentName(entity.getNationStudent().getStudentName())
                .content(entity.getConsumption().getContent())
                .amount(entity.getConsumption().getAmount())
                .state(entity.getState())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }
}
