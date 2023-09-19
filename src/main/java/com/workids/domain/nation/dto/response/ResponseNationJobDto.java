package com.workids.domain.nation.dto.response;

import com.workids.domain.job.entity.Job;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseNationJobDto {

    private String name; //직업명

    public static ResponseNationJobDto toDto(Job entity) {
        return ResponseNationJobDto.builder()
                .name(entity.getName())
                .build();
    }
}