package com.workids.domain.nation.entity;

import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationStudentJoinDto;
import com.workids.domain.nation.dto.response.ResponseNationStudentJoinDto;
import com.workids.domain.user.entity.Student;
import com.workids.domain.user.entity.Teacher;
import com.workids.global.config.BaseTimeEntity;
import com.workids.global.config.stateType.NationStateType;
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
public class NationStudent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nation_student_seq")
    @SequenceGenerator(name = "nation_student_seq", sequenceName = "nation_student_seq", allocationSize = 1)
    private Long nationStudentNum;

    @ManyToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_num")
    private Student student;

    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Nation nation;

    @Column(nullable = false)
    private int citizenNumber;

    @Column(nullable = false)
    private int creditRating;

    @Column(length = 20, nullable = false)
    private String studentName;

    // 생성할 때 default로 0 넣어주기
    @Column(nullable = false)
    private int state;

    public static NationStudent of(RequestNationStudentJoinDto dto, Student student, Nation nation, int citizenNumber) {
        return NationStudent.builder()
                .student(student)
                .nation(nation)
                .citizenNumber(citizenNumber)
                .creditRating(50) // default 50
                .studentName(student.getName())
                .state(student.getState()) // 가입완료: 1
                .build();
    }



}
