package com.workids.domain.law.entity;

import com.workids.domain.law.dto.request.RequestLawDto;
import com.workids.domain.nation.entity.Nation;
import com.workids.global.config.TimeEntity;
import com.workids.global.config.stateType.LawStateType;
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
public class Law extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "law_seq")
    @SequenceGenerator(name="law_seq", sequenceName = "law_seq", allocationSize = 1)
    @Column(name = "law_num")
    private Long lawNum;

    //FK
    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Nation nation;

    @Column(nullable = false, length = 4000)
    private String content;

    @Column(nullable = false)
    private int type;

    private Long fine;

    @Column(length = 500)
    private String penalty;

    @Column(nullable = false)
    private int state;

    public static Law toEntity(Nation nation, RequestLawDto dto) {
        return Law.builder()
                .nation(nation)
                .content(dto.getContent())
                .type(dto.getType())
                .fine(dto.getFine())
                .penalty(dto.getPenalty())
                .state(LawStateType.IN_USE)
                .build();
    }
}