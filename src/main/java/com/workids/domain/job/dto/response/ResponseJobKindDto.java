package com.workids.domain.job.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseJobKindDto {
    Long jobNum;
    String jobName;
}
