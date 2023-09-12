package com.workids.domain.nation.entity;

import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.user.entity.Teacher;
import com.workids.global.config.TimeEntity;
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
public class Nation extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "nation_seq")
    @SequenceGenerator(name="nation_seq", sequenceName = "nation_seq", allocationSize = 1)
    private Long nationNum;

    // FK
    @ManyToOne(targetEntity = Teacher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_num")
    private Teacher teacher;

    @Column(nullable = false, length = 60, unique = true)
    private String name;

    @Column(nullable = false, length = 60)
    private String moneyName;


    private int taxRate;

    @Column(nullable = false, length = 60)
    private String presidentName;

    @Column(nullable = false, length = 60)
    private String code;


    @Column(length = 60)
    private String school;
    private int grade;
    private int classRoom;

    private int payDay;

    @Column(nullable = false)
    private int state;

    private String startDate; // 나라 시작일
    private String endDate; // 나라 종료일




    public static Nation of(RequestNationJoinDto dto, Teacher teacher) {
        return Nation.builder()
                .teacher(teacher)
                .name(dto.getName())
                .moneyName(dto.getMoneyName())
                .taxRate(dto.getTaxRate())
                .presidentName(dto.getPresidentName())
                .code(dto.getCode())
                .school(dto.getSchool())
                .grade(dto.getGrade())
                .classRoom(dto.getClassRoom())
                .payDay(dto.getPayDay())
                .state(dto.getState())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

}
