package com.workids.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

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
    private int balance; // 잔액

    @Column(nullable = false)
    private int state; // 상품 가입 상태

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 개설일

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime endDate; // 만기일

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelDate; // 중도 해지일
}
