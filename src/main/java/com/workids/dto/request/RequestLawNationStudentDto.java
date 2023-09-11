package com.workids.dto.request;

import com.workids.domain.Law;
import com.workids.domain.LawNationStudent;
import com.workids.domain.NationStudent;
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

    private Long nationNum;
    private Long lawNationStudentNum;
    private Long lawNum;
    private Long nationStudentNum;
    private int citizenNumber;
    private int penaltyCompleteState;

    public static LawNationStudent of(Law law, NationStudent nationStudent, RequestLawNationStudentDto dto) {
        return LawNationStudent.builder()
                .law(law)
                .nationStudent(nationStudent)
                .penaltyCompleteState(dto.getPenaltyCompleteState())
                .build();
    }
}
