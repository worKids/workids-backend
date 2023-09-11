package com.workids.domain.user.entity;

import com.workids.domain.user.dto.request.TeacherJoinDto;
import com.workids.global.config.BaseTimeEntity;
import com.workids.global.config.Role;
import com.workids.global.config.stateType.UserStateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "teacher_seq")
    @SequenceGenerator(name="teacher_seq", sequenceName = "teacher_seq", allocationSize = 1)
    private Long teacherNum;

    @Column(nullable = false, length = 40, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(nullable = false)
    private int state;

    @Enumerated(EnumType.STRING)
    private Role role;
    public void addUserAuthority() {
        this.role = Role.TEACHER;
    }


    // password encoding
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }


    /*
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateT


     */
    @Builder
    public static Teacher of(TeacherJoinDto dto) {
        return Teacher.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .state(UserStateType.ACTIVE)
                .role(Role.TEACHER)
                .build();
    }
}
