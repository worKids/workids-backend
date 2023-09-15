package com.workids.domain.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 계좌 상세 거래내역 조회 ResponseDto
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBankTransactionListDto {
    private Long transactionHistoryNum; // 거래내역 고유 번호

    private String content; // 거래내역 내용

    private String category; // 거래내역 카테고리

    private int type; // 거래내역 유형

    private Long amount; // 금액

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime transactionDate; // 거래일
}
