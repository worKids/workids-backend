package com.workids.domain.job.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 직업 할 일
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_to_do_seq")
    @SequenceGenerator(name = "job_to_do_seq", sequenceName = "job_to_do_seq", allocationSize = 1)
    private Long jobToDoNum; // 직업 할 일 고유 번호

    // FK
    @ManyToOne(targetEntity = Job.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_num")
    private Job job; // 직업 고유 번호

    @Column(nullable = false, length = 4000)
    private String jobToDoContent; // 할 일 내용
}
