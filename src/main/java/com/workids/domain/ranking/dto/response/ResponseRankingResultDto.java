package com.workids.domain.ranking.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseRankingResultDto {

    //저축왕 랭킹
    private List<ResponseRankingDto> SavingRanking;
    //소비왕 랭킹
    private List<ResponseRankingDto> ConsumptionRanking;
    //자산왕 랭킹
    private List<ResponseRankingDto> AssetRanking;
    //벌금왕 랭킹
    private List<ResponseRankingDto> FineRanking;
}
