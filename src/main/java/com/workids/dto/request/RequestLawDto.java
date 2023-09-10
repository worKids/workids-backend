package com.workids.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLawDto {
    private String content;
    private int type;
    private int fine;
    private String penalty;
    private int state;
}
