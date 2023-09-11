package com.workids.domain.bank.entity;

import com.workids.domain.nation.entity.Nation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 은행 상품
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_seq")
    @SequenceGenerator(name = "bank_seq", sequenceName = "bank_seq", allocationSize = 1)
    private Long productNum; // 상품 고유 번호

    // FK
    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Nation nation; // 나라 고유 번호

    @Column(nullable = false)
    private int productType; // 상품 유형

    @Column(nullable = false, length = 100)
    private String productName; // 상품명

    @Column(length = 4000)
    private String productContent; // 상품 내용

    @Column(nullable = false)
    private int productPeriod; // 상품 기간

    @Column(nullable = false)
    private double interestRate; // 만기 이자율

    @Column(nullable = false)
    private double cancelInterestRate; // 중도 해지 이자율

    @Column(nullable = false)
    private int productState; // 상품 항목 상태

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 생성일

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 수정일
}
