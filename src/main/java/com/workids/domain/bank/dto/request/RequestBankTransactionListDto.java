package com.workids.domain.bank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 계좌 상세 거래내역 조회 RequestDto
 */
@Getter
@NoArgsConstructor
@ToString
public class RequestBankTransactionListDto {
    private Long bankNationStudentNum; // 은행-나라-학생 고유 번호
}
