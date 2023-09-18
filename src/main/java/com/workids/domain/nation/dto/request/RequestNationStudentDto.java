package com.workids.domain.nation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestNationStudentDto {
    private Long nationNum;
    private Long studentNum;
}
