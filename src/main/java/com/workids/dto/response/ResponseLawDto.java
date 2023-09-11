package com.workids.dto.response;

import com.workids.domain.Law;
import com.workids.domain.Nation;
import com.workids.dto.request.RequestLawDto;
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
    private Long lawNum;
    private String content;
    private int type;
    private int fine;
    private String penalty;

}
