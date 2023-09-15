package com.workids.domain.citizen.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class RequestCitizenDto {
    Long nationNum;

    int citizenNumber;
    String StudentName;
    String jobName;
    Long jobNum;
    Long asset;
    int creditRating;


}
