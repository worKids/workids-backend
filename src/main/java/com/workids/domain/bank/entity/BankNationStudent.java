package com.workids.domain.bank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.nation.entity.NationStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor; 
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 은행-나라-학생
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankNationStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_nation_student_seq")
    @SequenceGenerator(name = "bank_nation_student_seq", sequenceName = "bank_nation_student_seq", allocationSize = 1)
    private Long bankNationStudentNum; // 은행-나라-학생 고유 번호

    // FK
    @ManyToOne(targetEntity = Bank.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_num")
    private Bank bank; // 상품 고유 번호

    // FK
    @ManyToOne(targetEntity = NationStudent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_student_num")
    private NationStudent nationStudent; // 나라-학생 고유 번호

    @Column(nullable = false, length = 500)
    private String accountNumber; // 계좌 번호

    @Column(nullable = false)
    private Long balance; // 잔액

    @Column(nullable = false)
    private int state; // 상품 가입 상태

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false)
    private LocalDateTime createdDate; // 개설일

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(nullable = false)
    private LocalDateTime endDate; // 만기일

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime cancelDate; // 중도 해지일

    public static BankNationStudent of(Bank bank, NationStudent nationStudent, String accountNumber, Long balance, int state, LocalDateTime createdDate, LocalDateTime endDate){
        return BankNationStudent.builder()
                .bank(bank)
                .nationStudent(nationStudent)
                .accountNumber(accountNumber)
                .balance(balance)
                .state(state)
                .createdDate(createdDate)
                .endDate(endDate)
                .build();
    }
}
