package com.workids.domain.nation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ResponseNationInfoDto {
    /**
     * 나라 정보: 나라명, 화페명, 세율, 운영시작일, 운영종료일, 참여코드
     */

    private Long nationNum;
    private String name;
    private String moneyName;
    private int taxRate;
    private String code;

    private String presidentName; // 대통령 명

    private int payDay; // 월급 지급일
    private int state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate; // 나라 시작일
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime endDate; // 나라 종료일

    public static ResponseNationInfoDto toDto(Nation nation){
        return ResponseNationInfoDto.builder()
                .nationNum(nation.getNationNum())
                .name(nation.getName())
                .moneyName(nation.getMoneyName())
                .taxRate(nation.getTaxRate())
                .code(nation.getCode())
                .presidentName(nation.getPresidentName())
                .payDay(nation.getPayDay())
                .state(nation.getState())
                .startDate(nation.getStartDate())
                .endDate(nation.getEndDate())
                .build();
    }
}
