package com.workids.domain.user.dto.request;

import com.workids.global.config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class TeacherJoinDto {
    private String id;

    private String password;

    private String name;

    private String email;

    private String phone;

    // 생성할 때 default로 0 넣어주기
    private int state;

    private Role role;

}