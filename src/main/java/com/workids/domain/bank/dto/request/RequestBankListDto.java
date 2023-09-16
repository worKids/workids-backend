package com.workids.domain.bank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 전체 은행 상품 조회  RequestDto
 * 국민 예금 계좌 목록 조회 RequestDto - Teacher
 * 국민 주거래 계좌 목록 조회 RequestDto - Teacher
 */
@Getter
@NoArgsConstructor
@ToString
public class RequestBankListDto {
    private Long nationNum; // 나라 고유 번호
}
