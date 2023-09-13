package com.workids.domain.job.entity;

import com.workids.domain.job.dto.request.RequestJobDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.global.config.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 직업
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
    @SequenceGenerator(name = "job_seq", sequenceName = "job_seq", allocationSize = 1)
    private Long jobNum; // 직업 고유 번호

    // FK
    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Nation nation; // 나라 고유 번호

    @Column(nullable = false, length = 100)
    private String name; // 직업명

    @Column(nullable = false)
    private int salary; // 월급

    @Column(nullable = false)
    private int state; // 직업 항목 상태

    /*@CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate; // 생성일

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate; // 수정일*/
    @Builder
    public static Job toEntity(Nation nation, RequestJobDto jobDto){
        return Job.builder()
                .nation(nation)
                .name(jobDto.getName())
                .salary(jobDto.getSalary())
                .state(jobDto.getState())
                .build();
    }


}
