package com.workids.domain.bank.dto.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 국민 예금 계좌 목록 조회 ResponseDto - Teacher
 * 국민 주거래 계좌 목록 조회 ResponseDto - Teacher
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseBankTeacherJoinListDto {
    private int citizenNumber; // 학급 번호

    private String studentName; // 학생 이름

    private Long bankNationStudentNum; // 은행-나라-학생 고유 번호

    private String accountNumber; // 계좌번호

    private String productName; // 상품명

    private Long balance; // 잔액

    private double interestRate; // 만기 이자율

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdDate; // 개설일

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime endDate; // 만기일
}
