package com.workids.domain.nation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCitizenUpdateDto {
    private Long citizenNum; // PK

    private int citizenNumber; // 학급번호

    private String name; // 이름


}
