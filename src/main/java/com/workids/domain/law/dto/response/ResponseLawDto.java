package com.workids.domain.law.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.workids.domain.law.entity.Law;
import com.workids.domain.law.entity.LawNationStudent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseLawDto {
    //법 항목 고유 번호
    private Long lawNum;
    //법 내용
    private String content;
    //벌금, 벌칙 type
    private int type;
    //벌금 금액
    private int fine;
    //벌칙 내용
    private String penalty;
    //법 항목 생성일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    public ResponseLawDto toDto(Law entity){
        return ResponseLawDto.builder()
                .lawNum(entity.getLawNum())
                .content(entity.getContent())
                .type(entity.getType())
                .fine(entity.getFine())
                .penalty(entity.getPenalty())
                .createdDate(entity.getCreatedDate())
                .build();
    }


}
