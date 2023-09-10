package com.workids.domain;

import com.workids.dto.request.RequestLawDto;
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
public class Law {

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

    private int fine;

    @Column(length = 500)
    private String penalty;

    @Column(nullable = false)
    private int state;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    public static Law of(Nation nation, RequestLawDto dto) {
        return Law.builder()
                .nation(nation)
                .content(dto.getContent())
                .type(dto.getType())
                .fine(dto.getFine())
                .penalty(dto.getPenalty())
                .state(0)
                .build();
    }
}
