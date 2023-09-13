package com.workids.domain.job.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class RequestJobDto {

    Long jobNum;
    Long nationNum;
    String name;
    int salary;
    int state;
    String content;

}
