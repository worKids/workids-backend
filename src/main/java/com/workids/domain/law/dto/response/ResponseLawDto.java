package com.workids.domain.law.dto.response;

import com.workids.domain.law.entity.Law;
import com.workids.domain.law.entity.LawNationStudent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseLawDto {

    private Long lawNum;
    private String content;
    private int type;
    private int fine;
    private String penalty;

    public ResponseLawDto toDto(Law entity){
        return ResponseLawDto.builder()
                .lawNum(lawNum)
                .content(content)
                .type(type)
                .fine(fine)
                .penalty(penalty)
                .build();
    }


}
