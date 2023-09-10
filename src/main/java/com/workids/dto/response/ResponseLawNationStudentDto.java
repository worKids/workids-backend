package com.workids.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseLawNationStudentDto {
    private int citizenNumber;
    private String name;
    private String content;
    private int fine;
    private String penalty;
    private int penaltyCompleteState;
    private LocalDateTime createDate;
    private LocalDateTime penaltyEndDate;
}
