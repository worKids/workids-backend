package com.workids.domain.nation.entity;

import com.workids.domain.nation.dto.request.NationJoinDto;
import com.workids.domain.user.entity.Teacher;
import com.workids.global.config.TimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

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
/*
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;


 */

    public static Nation of(NationJoinDto dto) {
        return Nation.builder()
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
                .build();
    }

}
