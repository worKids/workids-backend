package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.dto.request.RequestNationInfoDto;
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

    private String name;
    private String moneyName;
    private int taxRate;
    private String code;

    private String startDate; // 나라 시작일
    private String endDate; // 나라 종료일

    public static ResponseNationInfoDto toDto(Nation nation){
        return ResponseNationInfoDto.builder()
                .name(nation.getName())
                .moneyName(nation.getMoneyName())
                .taxRate(nation.getTaxRate())
                .code(nation.getCode())
                .startDate(nation.getStartDate())
                .endDate(nation.getEndDate())
                .build();
    }
}
