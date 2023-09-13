package com.workids.domain.nation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestNationListDto {
    /**
     * teacher, student 공통 사용
     * num : teacherNum or studentNum
     */
    private Long num;

}
