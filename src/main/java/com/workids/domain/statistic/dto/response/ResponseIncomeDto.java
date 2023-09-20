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
public class ResponseIncomeDto {
    private List<String> menu;

    private List<Integer> percent;
    public static ResponseIncomeDto toDto(List<String> menu, List<Integer> percent) {
        return ResponseIncomeDto.builder()
                .menu(menu)
                .percent(percent)
                .build();
    }
}
