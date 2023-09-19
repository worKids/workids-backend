package com.workids.domain.consumption.entity;

import com.workids.domain.consumption.dto.request.RequestConsumptionDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.global.config.TimeEntity;
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
public class Consumption extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consumption_seq")
    @SequenceGenerator(name="consumption_seq", sequenceName = "consumption_seq", allocationSize = 1)
    @Column(name="consumption_num")
    private Long consumptionNum;

    //FK
    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Nation nation;

    @Column(length = 100)
    private String content;

    private Long amount;

    private int state;

    public static Consumption toEntity(Nation nation, RequestConsumptionDto dto) {
        return Consumption.builder()
                        .nation(nation)
                        .content(dto.getContent())
                        .amount(dto.getAmount())
                        .state(ConsumptionStateType.IN_USE)
                        .build();
    }

}