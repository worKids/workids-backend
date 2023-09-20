package com.workids.domain.nation.dto.response;

import com.workids.domain.law.dto.response.ResponseLawDto;
import com.workids.domain.law.entity.Law;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseNationLawDto {

    //법 내용
    private String content;

    public static ResponseNationLawDto toDto(Law entity) {
        return ResponseNationLawDto.builder()
                .content(entity.getContent())
                .build();
    }
}
