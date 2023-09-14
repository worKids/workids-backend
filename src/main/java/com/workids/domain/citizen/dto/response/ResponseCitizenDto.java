package com.workids.domain.citizen.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseCitizenDto {
            int citizenNumber;
            String studentName;
            String jobName;
            int 자산;
            int credit_rating;
}
