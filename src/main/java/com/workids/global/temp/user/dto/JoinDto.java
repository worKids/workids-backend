package com.workids.global.temp.user.dto;

import com.workids.global.config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class JoinDto {
    private String id;

    private String password;

    private String name;

    private String email;

    private String phone;

    // 생성할 때 default로 0 넣어주기
    private int state;

    private Role role;

    /*
    @Builder
    public static Teacher toEntity(Teacher dto) {
        return Teacher.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .state(dto.getState())
                .build();
    }

     */


}