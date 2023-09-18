package com.workids.domain.consumption.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.consumption.entity.Consumption;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseConsumptionDto {
    //소비 항목 고유 번호
    private long consumptionNum;
    //소비 항목 내용
    private String content;
    //소비 항목 금액
    private int amount;
    //소비 항목 생성일
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public ResponseConsumptionDto toDto(Consumption entity){
        return ResponseConsumptionDto.builder()
                .consumptionNum(entity.getConsumptionNum())
                .content(entity.getContent())
                .amount(entity.getAmount())
                .createdDate(entity.getCreatedDate())
                .build();
    }
}