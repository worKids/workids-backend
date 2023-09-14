package com.workids.domain.citizen.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseCitizenCreditDto {

    int citizenNumber;
    String studentName;
    int creditRating;
}
