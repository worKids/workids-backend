package com.workids.domain.citizen.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseCitizenDto {
            int citizenNumber;
            String studentName;
            String jobName;
            Long asset;
            int credit_rating;
}
