package com.workids.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseLawDto {
    private long lawNum;
    private String content;
    private int fine;
    private String penalty;
    private LocalDateTime updateDate;

}
