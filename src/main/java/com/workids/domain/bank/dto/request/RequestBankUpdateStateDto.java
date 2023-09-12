package com.workids.domain.bank.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 은행 상품 삭제 RequestDto
 */
@Getter
@NoArgsConstructor
@ToString
public class RequestBankUpdateStateDto {
    private Long productNum; // 상품 고유 번호
}
