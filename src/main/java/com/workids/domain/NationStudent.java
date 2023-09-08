package com.workids.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NationStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nation_student_seq")
    @SequenceGenerator(name = "nation_student_seq", sequenceName = "nation_student_seq", allocationSize = 1)
    private Long nationStudentNum;

    @ManyToOne(targetEntity = Student.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_num")
    private Student student;

    @ManyToOne(targetEntity = Nation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "nation_num")
    private Long NationNum;

    @Column(nullable = false)
    private int citizenNumber;

    @Column(nullable = false)
    private int creditRating;

    @Column(length = 20, nullable = false)
    private String studentName;

    // 생성할 때 default로 0 넣어주기
    @Column(nullable = false)
    private int state;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
