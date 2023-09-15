package com.workids.domain.bank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 예금 계좌 목록 조회 RequestDto - Student
 * 주거래 계좌 목록 조회 RequestDto - Student
 */
@Getter
@NoArgsConstructor
@ToString
public class RequestBankStudentJoinListDto {
    private Long nationStudentNum; // 나라-학생 고유 번호
}
