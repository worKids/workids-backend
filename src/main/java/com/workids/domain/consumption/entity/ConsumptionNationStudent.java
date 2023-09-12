package com.workids.domain.consumption.entity;

import com.workids.domain.nation.entity.NationStudent;
import com.workids.global.config.BaseTimeEntity;
import com.workids.global.config.stateType.ConsumptionStateType;
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
public class ConsumptionNationStudent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consumption_nation_student_seq")
    @SequenceGenerator(name="consumption_nation_student_seq", sequenceName = "consumption_nation_student_seq", allocationSize = 1)
    @Column(name="consumption_nation_student_num")
    private Long consumptionNationStudentNum;

    //Fk
    @ManyToOne(targetEntity = Consumption.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "consumption_num")
    private Consumption consumption;

    //FK
    @ManyToOne(targetEntity = NationStudent.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_student_num")
    private NationStudent nationStudent;

    private int state;

    public static ConsumptionNationStudent toEntity(Consumption consumption, NationStudent nationStudent) {
        return ConsumptionNationStudent.builder()
                .consumption(consumption)
                .nationStudent(nationStudent)
                .state(ConsumptionStateType.BEFORE_CHECK)
                .build();
    }

}

