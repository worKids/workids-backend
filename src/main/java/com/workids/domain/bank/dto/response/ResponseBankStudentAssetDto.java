package com.workids.domain.bank.dto.response;

import lombok.*;

/**
 * 총 자산 조회 ResponseDto - Student
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBankStudentAssetDto {
    private Long asset; // 자산 합

    public static ResponseBankStudentAssetDto toDto(Long sum){
        return ResponseBankStudentAssetDto.builder()
                .asset(sum)
                .build();
    }
}
