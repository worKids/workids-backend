package com.workids.domain.statistic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseIncomeExpendDto {
    private List<String> menu;

    private List<Integer> percent;

    public static ResponseIncomeExpendDto toDto(List<String> menu, List<Integer> percent) {
        return ResponseIncomeExpendDto.builder()
                .menu(menu)
                .percent(percent)
                .build();
    }
}
