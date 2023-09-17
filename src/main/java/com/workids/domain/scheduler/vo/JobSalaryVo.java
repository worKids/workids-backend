package com.workids.domain.scheduler.vo;

import lombok.*;

/**
 * 월급 지급 처리 Scheduler Vo
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JobSalaryVo {
    private Long nationStudentNum; // 나라-학생 고유 번호

    private String jobName; // 직업명

    private int salary; // 월급
}
