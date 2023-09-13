package com.workids.domain.bank.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 거래내역(계좌)
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_history_seq")
    @SequenceGenerator(name = "transaction_history_seq", sequenceName = "transaction_history_seq", allocationSize = 1)
    private Long transactionHistoryNum; // 거래내역 고유 번호

    // FK
    @ManyToOne(targetEntity = BankNationStudent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_nation_student_num")
    private BankNationStudent bankNationStudent; // 은행-나라-학생 고유 번호

    @Column(nullable = false, length = 200)
    private String content; // 거래내역 내용

    @Column(nullable = false, length = 100)
    private String category; // 거래내역 카테고리

    @Column(nullable = false)
    private int type; // 거래내역 유형

    @Column(nullable = false)
    private Long amount; // 금액

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime transactionDate; // 거래일

    public static TransactionHistory of(BankNationStudent bankNationStudent, String content, String category, int type, Long amount){
        return TransactionHistory.builder()
                .bankNationStudent(bankNationStudent)
                .content(content)
                .category(category)
                .type(type)
                .amount(amount)
                .build();
    }
}
