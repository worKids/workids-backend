package com.workids.domain.consumption.dto.response;

import com.workids.domain.consumption.entity.Consumption;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseConsumptionDto {
    private long consumptionNum;
    private String content;
    private int amount;
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