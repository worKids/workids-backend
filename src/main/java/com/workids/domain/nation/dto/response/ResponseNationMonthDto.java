package com.workids.domain.nation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseNationMonthDto {
    private Set<Integer> month;
    public static ResponseNationMonthDto toDto(Set<Integer> list) {
        return ResponseNationMonthDto.builder()
                .month(list)
                .build();
    }
}

