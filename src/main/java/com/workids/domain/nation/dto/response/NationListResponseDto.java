package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.dto.request.NationListALLDto;
import com.workids.domain.nation.entity.Nation;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NationListResponseDto {
    /**
     * 나라이름, 국민
     */

    private String name;
    /**
     * 나라 전체 조회
     */

    public static NationListResponseDto of(Nation nation){
        return NationListResponseDto.builder()
                .name(nation.getName())
                .build();
    }

}
