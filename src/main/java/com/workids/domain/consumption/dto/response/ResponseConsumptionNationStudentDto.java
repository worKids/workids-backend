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

    private Long consumptionNationStudentNum;
    private int citizenNumber;
    private String studentName;
    private String content;
    private int amount;
    private int state; //소비 신청 상태
    private LocalDateTime createdDate;

    public ResponseConsumptionNationStudentDto toDto(ConsumptionNationStudent entity){
        return ResponseConsumptionNationStudentDto.builder()
                .consumptionNationStudentNum(entity.getConsumptionNationStudentNum())
                .citizenNumber(entity.getNationStudent().getCitizenNumber())
                .studentName(entity.getNationStudent().getStudentName())
                .content(entity.getConsumption().getContent())
                .amount(entity.getConsumption().getAmount())
                .state(entity.getState())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}
