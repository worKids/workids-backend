package com.workids.domain.nation.dto.request;

import com.workids.domain.user.entity.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@ToString
public class NationJoinDto {

    private Long teacherNum;

    private String name;

    private String moneyName;

    private int taxRate;

    private String presidentName;

    private String code;

    private String school;
    private int grade;
    private int classRoom;

    private int payDay;

    private int state;
}
