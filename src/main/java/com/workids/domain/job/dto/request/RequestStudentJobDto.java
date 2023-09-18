package com.workids.domain.job.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class RequestStudentJobDto {
    Long nationStudentNum;
    Long jobNum;
    Long nationNum;
    String name;
    int citizenNumber;
    int state;

}
