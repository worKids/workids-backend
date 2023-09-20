package com.workids.domain.ranking.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.QBank;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.consumption.entity.QConsumption;
import com.workids.domain.consumption.entity.QConsumptionNationStudent;
import com.workids.domain.law.entity.QLaw;
import com.workids.domain.law.entity.QLawNationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.ranking.dto.request.RequestRankingDto;
import com.workids.domain.ranking.dto.response.ResponseRankingDto;
import com.workids.domain.ranking.dto.response.ResponseRankingResultDto;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.config.stateType.ConsumptionStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RankingService {

    private final JPAQueryFactory queryFactory;

    public ResponseRankingResultDto getRanking(RequestRankingDto dto){

        QNationStudent nationStudent = QNationStudent.nationStudent;
        QConsumption consumption = QConsumption.consumption;
        QConsumptionNationStudent consumptionNationStudent = QConsumptionNationStudent.consumptionNationStudent;
        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;
        QBank bank = QBank.bank;
        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;
        QLaw law = QLaw.law;

        LocalDate currentDate = LocalDate.now();

        ResponseRankingResultDto resultDto = new ResponseRankingResultDto(); //결과 Dto

        //자산왕(전체밖에 없음)
        List<Tuple> result2 = queryFactory
                .select(
                        nationStudent.studentName,
                        nationStudent.citizenNumber,
                        bankNationStudent.balance.sum()
                )
                .from(bankNationStudent)
                .join(nationStudent)
                .on(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                .where(
                        bankNationStudent.state.eq(BankStateType.IN_USE),
                        nationStudent.nation.nationNum.eq(dto.getNationNum())
                )
                .groupBy(
                        bankNationStudent.nationStudent.nationStudentNum,
                        nationStudent.citizenNumber,
                        nationStudent.studentName
                )
                .orderBy(bankNationStudent.balance.sum().desc())
                .limit(5)
                .fetch();

        List<ResponseRankingDto> assetRanking = new ArrayList<>();
        for (Tuple row : result2) {
            String studentName = row.get(nationStudent.studentName);
            int citizenNumber = row.get(nationStudent.citizenNumber);
            Long sumAsset = row.get(bankNationStudent.balance.sum());

            assetRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumAsset));
        }

        resultDto.setAssetRanking(assetRanking);

        //월
        if(dto.getType()=='m') {
            int currentMonth = currentDate.getMonthValue();

            //저축왕 //이 달에 가장 많은 예금을 저축
            List<Tuple> result = queryFactory
                    .select(
                            nationStudent.studentName,
                            nationStudent.citizenNumber,
                            bankNationStudent.balance.sum()
                    )
                    .from(bank)
                    .join(bankNationStudent)
                    .on(bank.productNum.eq(bankNationStudent.bank.productNum))
                    .join(nationStudent)
                    .on(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                    .where(
                            bankNationStudent.createdDate.month().eq(currentMonth),
                            bankNationStudent.state.eq(BankStateType.IN_USE),
                            bank.productType.eq(BankStateType.DEPOSIT_ACCOUNT),
                            nationStudent.nation.nationNum.eq(dto.getNationNum())
                    )
                    .groupBy(
                            bankNationStudent.nationStudent.nationStudentNum,
                            nationStudent.citizenNumber,
                            nationStudent.studentName
                    )
                    .orderBy(bankNationStudent.balance.sum().desc())
                    .limit(5) // 최상위 5개 결과만 가져오기
                    .fetch();

            List<ResponseRankingDto> savingRanking = new ArrayList<>();
            for (Tuple row : result) {
                String studentName = row.get(nationStudent.studentName);
                int citizenNumber = row.get(nationStudent.citizenNumber);
                Long sumSaving = row.get(bankNationStudent.balance.sum());

                savingRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumSaving));
            }

            resultDto.setSavingRanking(savingRanking);


            //소비왕
            List<Tuple> result1 = queryFactory
                    .select(
                            nationStudent.studentName,
                            nationStudent.citizenNumber,
                            consumption.amount.sum()
                    )
                    .from(consumption)
                    .join(consumptionNationStudent)
                    .on(consumption.consumptionNum.eq(consumptionNationStudent.consumption.consumptionNum))
                    .join(nationStudent)
                    .on(consumptionNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                    .where(
                            consumptionNationStudent.updatedDate.month().eq(currentMonth), //현재 달에 승인된 내역들만
                            consumptionNationStudent.state.eq(ConsumptionStateType.APPROVAL),
                            nationStudent.nation.nationNum.eq(dto.getNationNum())
                    )
                    .groupBy(
                            consumptionNationStudent.nationStudent.nationStudentNum,
                            nationStudent.citizenNumber,
                            nationStudent.studentName
                    )
                    .orderBy(consumption.amount.sum().desc())
                    .limit(5) // 최상위 5개 결과만 가져오기
                    .fetch();

            List<ResponseRankingDto> consumptionRanking = new ArrayList<>();
            for (Tuple row : result1) {
                String studentName = row.get(nationStudent.studentName);
                int citizenNumber = row.get(nationStudent.citizenNumber);
                Long sumAmount = row.get(consumption.amount.sum());

                consumptionRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumAmount));
            }

            resultDto.setConsumptionRanking(consumptionRanking);


            //벌금왕
            List<Tuple> result3 = queryFactory
                    .select(
                            nationStudent.studentName,
                            nationStudent.citizenNumber,
                            law.fine.sum()
                    )
                    .from(law)
                    .join(lawNationStudent)
                    .on(law.lawNum.eq(lawNationStudent.law.lawNum))
                    .join(nationStudent)
                    .on(lawNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                    .where(
                            lawNationStudent.createdDate.month().eq(currentMonth),
                            nationStudent.nation.nationNum.eq(dto.getNationNum())
                    )
                    .groupBy(
                            lawNationStudent.nationStudent.nationStudentNum,
                            nationStudent.citizenNumber,
                            nationStudent.studentName
                    )
                    .orderBy(law.fine.sum().desc())
                    .limit(5)
                    .fetch();

            List<ResponseRankingDto> fineRanking = new ArrayList<>();
            for (Tuple row : result3) {
                String studentName = row.get(nationStudent.studentName);
                int citizenNumber = row.get(nationStudent.citizenNumber);
                Long sumFine = row.get(law.fine.sum());

                fineRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumFine));
            }

            resultDto.setFineRanking(fineRanking);

        }

        //전체
        if(dto.getType()=='t') {

            //저축왕
            List<Tuple> result = queryFactory
                    .select(
                            nationStudent.studentName,
                            nationStudent.citizenNumber,
                            bankNationStudent.balance.sum()
                    )
                    .from(bank)
                    .join(bankNationStudent)
                    .on(bank.productNum.eq(bankNationStudent.bank.productNum))
                    .join(nationStudent)
                    .on(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                    .where(
                            bankNationStudent.state.eq(BankStateType.IN_USE),
                            bank.productType.eq(BankStateType.DEPOSIT_ACCOUNT),
                            nationStudent.nation.nationNum.eq(dto.getNationNum())
                    )
                    .groupBy(
                            bankNationStudent.nationStudent.nationStudentNum,
                            nationStudent.citizenNumber,
                            nationStudent.studentName
                    )
                    .orderBy(bankNationStudent.balance.sum().desc())
                    .limit(5) // 최상위 5개 결과만 가져오기
                    .fetch();

            List<ResponseRankingDto> savingRanking = new ArrayList<>();
            for (Tuple row : result) {
                String studentName = row.get(nationStudent.studentName);
                int citizenNumber = row.get(nationStudent.citizenNumber);
                Long sumSaving = row.get(bankNationStudent.balance.sum());

                savingRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumSaving));
            }

            resultDto.setSavingRanking(savingRanking);


            //소비왕
            List<Tuple> result1 = queryFactory
                    .select(
                            nationStudent.studentName,
                            nationStudent.citizenNumber,
                            consumption.amount.sum()
                    )
                    .from(consumption)
                    .join(consumptionNationStudent)
                    .on(consumption.consumptionNum.eq(consumptionNationStudent.consumption.consumptionNum))
                    .join(nationStudent)
                    .on(consumptionNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                    .where(
                            consumptionNationStudent.state.eq(ConsumptionStateType.APPROVAL),
                            nationStudent.nation.nationNum.eq(dto.getNationNum())
                    )
                    .groupBy(
                            consumptionNationStudent.nationStudent.nationStudentNum,
                            nationStudent.citizenNumber,
                            nationStudent.studentName
                    )
                    .orderBy(consumption.amount.sum().desc())
                    .limit(5) // 최상위 5개 결과만 가져오기
                    .fetch();

            List<ResponseRankingDto> consumptionRanking = new ArrayList<>();
            for (Tuple row : result1) {
                String studentName = row.get(nationStudent.studentName);
                int citizenNumber = row.get(nationStudent.citizenNumber);
                Long sumAmount = row.get(consumption.amount.sum());

                consumptionRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumAmount));
            }

            resultDto.setConsumptionRanking(consumptionRanking);

            //벌금왕
            List<Tuple> result3 = queryFactory
                    .select(
                            nationStudent.studentName,
                            nationStudent.citizenNumber,
                            law.fine.sum()
                    )
                    .from(law)
                    .join(lawNationStudent)
                    .on(law.lawNum.eq(lawNationStudent.law.lawNum))
                    .join(nationStudent)
                    .on(lawNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                    .where(
                            nationStudent.nation.nationNum.eq(dto.getNationNum())
                    )
                    .groupBy(
                            lawNationStudent.nationStudent.nationStudentNum,
                            nationStudent.citizenNumber,
                            nationStudent.studentName
                    )
                    .orderBy(law.fine.sum().desc())
                    .limit(5)
                    .fetch();

            List<ResponseRankingDto> fineRanking = new ArrayList<>();
            for (Tuple row : result3) {
                String studentName = row.get(nationStudent.studentName);
                int citizenNumber = row.get(nationStudent.citizenNumber);
                Long sumFine = row.get(law.fine.sum());

                fineRanking.add(new ResponseRankingDto(studentName,citizenNumber,sumFine));
            }

            resultDto.setFineRanking(fineRanking);

        }

        return resultDto;
    }
}
