package com.workids.dto.response;

import com.workids.domain.Law;
import com.workids.domain.LawNationStudent;
import com.workids.domain.NationStudent;
import com.workids.dto.request.RequestLawNationStudentDto;
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

    private Long lawNationStudentNum;
    private int citizenNumber;
    private String name;
    private String content;
    private int fine;
    private String penalty;
    private int penaltyCompleteState;

}
