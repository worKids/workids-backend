package com.workids.domain.job.entity;

import com.workids.domain.nation.entity.NationStudent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 직업-나라-학생
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobNationStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_nation_student_seq")
    @SequenceGenerator(name = "job_nation_student_seq", sequenceName = "job_nation_student_seq", allocationSize = 1)
    private Long jobNationStudentNum; // 직업-나라-학생 고유 번호

    // FK
    @ManyToOne(targetEntity = Job.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_num")
    private Job job; // 직업 고유 번호

    // FK
    @ManyToOne(targetEntity = NationStudent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_student_num")
    private NationStudent nationStudent; // 나라-학생 고유 번호

    @Column(nullable = false)
    private int state; // 재직 상태

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 생성일

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate; // 종료일
}
