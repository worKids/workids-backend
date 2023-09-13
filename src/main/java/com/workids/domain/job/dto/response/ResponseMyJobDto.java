package com.workids.domain.job.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseMyJobDto {
    String name;
    int salary;
    LocalDateTime createDate;
    LocalDateTime updateDate;
}
