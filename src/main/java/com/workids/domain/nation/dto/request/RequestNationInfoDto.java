package com.workids.domain.nation.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestNationInfoDto {
    /**
     * 나라 고유번호
     */
    private Long nationNum;

}
