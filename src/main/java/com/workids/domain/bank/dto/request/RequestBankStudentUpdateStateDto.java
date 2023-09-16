package com.workids.domain.bank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 예금 계좌 중도 해지 RequestDto - Student
 */
@Getter
@NoArgsConstructor
@ToString
public class RequestBankStudentUpdateStateDto {
    private Long bankNationStudentNum; // 은행-나라-학생 고유 번호

    private Long nationStudentNum; // 나라-학생 고유 번호
}
