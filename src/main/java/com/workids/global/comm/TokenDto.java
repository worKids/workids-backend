package com.workids.global.comm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    /**
     * 학생 PK
     * 나라 PK
     * 나라 - 학생 PK
     *
     * 선생 PK
     * 나라PK
     *
     */
    private String accessToken;

}