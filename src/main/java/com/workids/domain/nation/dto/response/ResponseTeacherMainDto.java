package com.workids.domain.nation.dto.response;

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
    private LocalDateTime startDate; // 나라 시작일
    private LocalDateTime endDate; // 나라 종료일
    private int totalCitizen;

    /**
     * 법 정보 - 법 이름 리스트
     */
    private String content; //법 내용
    private String penalty; //벌칙 내용

    /**
     * 직업 정보 - 직업 이름 리스트
     */
    private String name; // 직업명


    public ResponseTeacherMainDto toDto(Nation nation, Law law, Job job, int totalCitizen){
        return ResponseTeacherMainDto.builder()
                .moneyName(nation.getMoneyName())
                .taxRate(nation.getTaxRate())
                .startDate(nation.getStartDate())
                .endDate(nation.getStartDate())
                .totalCitizen(totalCitizen)
                .content(law.getContent())
                .penalty(law.getPenalty())
                .name(job.getName())
                .build();
    }
}
