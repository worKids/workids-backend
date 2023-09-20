package com.workids.domain.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMonthlyStatisticDto {
    private List<YearMonth> month;
    private List<Long> income;
    private List<Long> expend;

    public static ResponseMonthlyStatisticDto toDto(List<YearMonth> month, List<Long> income, List<Long> expend) {

        return ResponseMonthlyStatisticDto.builder()
                .month(month)
                .income(income)
                .expend(expend)
                .build();
    }
}
