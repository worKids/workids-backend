package com.workids.dto.request;

import com.workids.domain.Law;
import com.workids.domain.Nation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLawDto {
    private Long nationNum;
    private String content;
    private int type;
    private int fine;
    private String penalty;
    private Long lawNum; //수정할때 필요
    private int state;

    public static Law of(Nation nation, RequestLawDto dto) {
        return Law.builder()
                .nation(nation)
                .content(dto.getContent())
                .type(dto.getType())
                .fine(dto.getFine())
                .penalty(dto.getPenalty())
                .state(0)
                .build();
    }
}
