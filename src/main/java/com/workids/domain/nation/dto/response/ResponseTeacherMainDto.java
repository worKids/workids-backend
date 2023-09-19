package com.workids.domain.nation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.job.entity.Job;
import com.workids.domain.law.entity.Law;
import com.workids.domain.nation.entity.Nation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTeacherMainDto {
    /**
     * 나라 정보
     * 회페명, 세율, 운영시작일, 운영 종료일, 국민수
     */
    private String moneyName; // 화폐명
    private int taxRate; // 세율
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate; // 나라 시작일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime endDate; // 나라 종료일

    private int totalCitizen;



    public static ResponseTeacherMainDto toDto(Nation nation, int totalCitizen){
        return ResponseTeacherMainDto.builder()
                .moneyName(nation.getMoneyName())
                .taxRate(nation.getTaxRate())
                .startDate(nation.getStartDate())
                .endDate(nation.getStartDate())
                .totalCitizen(totalCitizen)
                .build();
    }
}
