package com.workids.domain.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.bank.entity.Bank;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 전체 은행 상품 조회 ResponseDto - Teacher
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBankTeacherListDto {
    private Long productNum; // 상품 고유 번호

    private int productType; // 상품 유형

    private String productName; // 상품명

    private String productContent; // 상품 내용

    private int productPeriod; // 상품 기간

    private double interestRate; // 만기 이자율

    private double cancelInterestRate; // 중도 해지 이자율

    private int productState; // 상품 항목 상태

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate; // 생성일

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate; // 종료일

    public static ResponseBankTeacherListDto toDto(Bank bank){
        return ResponseBankTeacherListDto.builder()
                .productNum(bank.getProductNum())
                .productType(bank.getProductType())
                .productName(bank.getProductName())
                .productContent(bank.getProductContent())
                .productPeriod(bank.getProductPeriod())
                .interestRate(bank.getInterestRate())
                .cancelInterestRate(bank.getCancelInterestRate())
                .productState(bank.getProductState())
                .createdDate(bank.getCreatedDate())
                .endDate(bank.getEndDate())
                .build();
    }
}
