package com.workids.domain.user.entity;

import com.workids.domain.user.dto.request.StudentJoinDto;
import com.workids.global.config.BaseTimeEntity;
import com.workids.global.config.Role;
import com.workids.global.config.stateType.UserStateType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Student extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_seq")
    @SequenceGenerator(name="student_seq", sequenceName = "student_seq", allocationSize = 1)
    @Column(name="student_num")
    private Long studentNum;

    @Column(nullable = false, length = 40, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 60)
    private String email;

    @Column(length = 50)
    private String phone;

    // 생성할 때 default로 0 넣어주기
    //@Column(nullable = false)
    private int state;

    // 추가
    @Enumerated(EnumType.STRING)
    private Role role;

    public void addUserAuthority() {
        this.role = Role.STUDENT;
    }

    @Column(nullable = false, length = 20)
    private String registNumber;

    /*
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
     */

    // password encoding
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    @Builder
    public static Student of(StudentJoinDto dto) {
        return Student.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .state(UserStateType.ACTIVE)
                .role(Role.STUDENT)
                .registNumber(dto.getRegistNumber())
                .build();
    }


}
