package com.workids.domain.job.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseJobDto {

    String name;
    String jobToDoContent;
    int salary;

}
