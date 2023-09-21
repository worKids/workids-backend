package com.workids.domain.bank.dto.response;

import lombok.*;

/**
 * 신용도 조회 ResponseDto - Student
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBankStudentCreditRatingDto {
    private int creditRating; // 신용도

    public static ResponseBankStudentCreditRatingDto toDto(int creditRating){
        return ResponseBankStudentCreditRatingDto.builder()
                .creditRating(creditRating)
                .build();
    }
}
