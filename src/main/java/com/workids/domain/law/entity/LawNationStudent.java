package com.workids.domain.law.entity;

import com.workids.domain.law.dto.request.RequestLawDto;
import com.workids.domain.law.dto.request.RequestLawNationStudentDto;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.global.config.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawNationStudent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "law_nation_student_seq")
    @SequenceGenerator(name="law_nation_student_seq", sequenceName = "law_nation_student_seq", allocationSize = 1)
    @Column(name = "law_nation_student_num")
    private Long lawNationStudentNum;

    //FK
    @ManyToOne(targetEntity = Law.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "law_num")
    private Law law;

    //FK
    @ManyToOne(targetEntity = NationStudent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_student_num")
    private NationStudent nationStudent;

    private int penaltyCompleteState;

    /*@CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime penaltyEndDate;*/

   public static LawNationStudent toEntity(Law law, NationStudent nationStudent, RequestLawNationStudentDto dto) {
        return LawNationStudent.builder()
                .law(law)
                .nationStudent(nationStudent)
                .penaltyCompleteState(dto.getPenaltyCompleteState())
                .build();
    }
}