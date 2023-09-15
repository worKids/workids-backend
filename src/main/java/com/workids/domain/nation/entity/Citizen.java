package com.workids.domain.nation.entity;


import com.workids.domain.nation.dto.request.RequestCitizenJoinDto;
import com.workids.domain.nation.dto.request.RequestCitizenUpdateDto;
import com.workids.domain.nation.dto.request.RequestNationUpdateDto;
import com.workids.global.config.TimeEntity;
import com.workids.global.config.stateType.NationStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Citizen extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "citizen_seq")
    @SequenceGenerator(name="citizen_seq", sequenceName = "citizen_seq", allocationSize = 1)
    private Long citizenNum;

    // FK
    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    Nation nation;

    @Column(nullable = false)
    private int citizenNumber;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private int state;

    public static Citizen of(RequestCitizenJoinDto dto, Nation nation) {
        return Citizen.builder()
                .nation(nation)
                .citizenNumber(dto.getCitizenNumber())
                .name(dto.getName())
                .birthDate(dto.getBirthDate())
                .state(NationStateType.LEAVE_NATION)
                .build();
    }

    // 국민목록 수정
    public void updateState(RequestCitizenUpdateDto dto){
        this.name = dto.getName();
        this.citizenNumber = dto.getCitizenNumber();
    }

}
