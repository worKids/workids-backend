package com.workids.domain.bank.dto.request;

import com.workids.domain.nation.entity.NationStudent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 은행 상품 가입(예금) RequestDto - Student
 */
@Getter
@NoArgsConstructor
@ToString
public class RequestBankStudentCreateDto {
    private Long productNum; // 상품 고유 번호

    private Long nationStudentNum; // 나라-학생 고유 번호

    private Long depositAmount; // 예금 금액
}
