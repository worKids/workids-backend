package com.workids.domain.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStatisticDto {
    private ResponseAssetDto asset;
    private ResponseExpendDto expend;
    private ResponseIncomeDto income;
    private ResponseIncomeExpendDto incomeExpend;
    private ResponseMonthlyStatisticDto monthly;
    public static ResponseStatisticDto toDto(ResponseAssetDto assetDto, ResponseExpendDto expendDto,
                                      ResponseIncomeDto incomeDto, ResponseIncomeExpendDto incomeExpendDto,
                                      ResponseMonthlyStatisticDto monthlyDto) {
        return ResponseStatisticDto.builder()
                .asset(assetDto)
                .expend(expendDto)
                .income(incomeDto)
                .incomeExpend(incomeExpendDto)
                .monthly(monthlyDto)
                .build();
    }
}
