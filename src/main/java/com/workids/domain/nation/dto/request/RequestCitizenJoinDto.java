package com.workids.domain.nation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCitizenJoinDto {

    private Long nationNum;

    private int citizenNumber;

    private String name;

    private String birthDate;
}
