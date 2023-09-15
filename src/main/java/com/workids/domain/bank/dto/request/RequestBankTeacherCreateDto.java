package com.workids.domain.bank.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 은행 상품 등록 RequestDto - Teacher
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestBankTeacherCreateDto {
    private Long nationNum; // 나라 고유 번호

    private int productType; // 상품 유형

    private String productName; // 상품명

    private String productContent; // 상품 내용

    private int productPeriod; // 상품 기간

    private double interestRate; // 만기 이자율

    private double cancelInterestRate; // 중도 해지 이자율
}
