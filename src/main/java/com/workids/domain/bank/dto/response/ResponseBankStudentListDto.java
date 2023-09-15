package com.workids.domain.bank.dto.response;

import com.workids.domain.bank.entity.Bank;
import lombok.*;

/**
 * 전체 은행 상품 조회 ResponseDto - Student
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBankStudentListDto {
    private Long productNum; // 상품 고유 번호

    private int productType; // 상품 유형

    private String productName; // 상품명

    private String productContent; // 상품 내용

    private int productPeriod; // 상품 기간

    private double interestRate; // 만기 이자율

    private double cancelInterestRate; // 중도 해지 이자율

    public static ResponseBankStudentListDto toDto(Bank bank){
        return ResponseBankStudentListDto.builder()
                .productNum(bank.getProductNum())
                .productType(bank.getProductType())
                .productName(bank.getProductName())
                .productContent(bank.getProductContent())
                .productPeriod(bank.getProductPeriod())
                .interestRate(bank.getInterestRate())
                .cancelInterestRate(bank.getCancelInterestRate())
                .build();
    }
}
