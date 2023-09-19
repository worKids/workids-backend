package com.workids.domain.citizen.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseCitizenInfoDto {
    int citizenNumber;
    String studentName;
    String name;
    int creditRating;
}