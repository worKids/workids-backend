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
public class ResponseAssetDto {
    private List<String> menu;

    private List<Integer> percent;

    public static ResponseAssetDto toDto(List<String> menu, List<Integer> percent) {
        return ResponseAssetDto.builder()
                .menu(menu)
                .percent(percent)
                .build();
    }
}
