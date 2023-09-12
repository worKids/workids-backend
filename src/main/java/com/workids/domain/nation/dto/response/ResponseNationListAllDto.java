package com.workids.domain.nation.dto.response;

import com.workids.domain.nation.entity.Nation;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseNationListAllDto {
    /**
     * 생성된 나라 조회: 나라이름
     */

    private String name;

    public static ResponseNationListAllDto of(Nation nation){
        return ResponseNationListAllDto.builder()
                .name(nation.getName())
                .build();
    }

}
