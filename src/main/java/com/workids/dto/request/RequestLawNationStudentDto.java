package com.workids.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLawNationStudentDto {
    private int citizenNumber;
    private long lawNum;
    private int penaltyCompleteState;
}
