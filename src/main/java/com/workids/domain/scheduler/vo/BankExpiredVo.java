package com.workids.domain.scheduler.vo;

import lombok.*;

/**
 * 예금 계좌 만기 처리 Scheduler VO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankExpiredVo {
    private Long bankNationStudentNum; // 은행-나라-학생 고유 번호(예금 계좌)

    private Long nationStudentNum; // 나라-학생 고유 번호
    
    private Long balance; // 잔액(예금 금액)

    private double interestRate; // 만기 이자율
}
