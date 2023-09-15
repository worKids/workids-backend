package com.workids.domain.consumption.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.bank.service.StudentBankService;
import com.workids.domain.consumption.dto.request.RequestConsumptionDto;
import com.workids.domain.consumption.dto.request.RequestConsumptionNationStudentDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionNationStudentDto;
import com.workids.domain.consumption.entity.Consumption;
import com.workids.domain.consumption.entity.ConsumptionNationStudent;
import com.workids.domain.consumption.entity.QConsumption;
import com.workids.domain.consumption.entity.QConsumptionNationStudent;
import com.workids.domain.consumption.repository.ConsumptionNationStudentRepository;
import com.workids.domain.consumption.repository.ConsumptionRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.service.NationStudentService;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.config.stateType.ConsumptionStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherConsumptionService {

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private ConsumptionNationStudentRepository consumptionNationStudentRepository;

    @Autowired
    private NationRepository nationRepository;

    @Autowired
    private StudentBankService studentBankService;

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 소비 항목 조회
     * */
    public List<ResponseConsumptionDto> getAllConsumptions(RequestConsumptionDto dto){
        QConsumption consumption = QConsumption.consumption;

        List<ResponseConsumptionDto> consumptionList = queryFactory.select(
                        Projections.constructor(
                                ResponseConsumptionDto.class,
                                consumption.consumptionNum,
                                consumption.content,
                                consumption.amount,
                                consumption.createdDate
                        )
                )
                .from(consumption)
                .where(consumption.nation.nationNum.eq(dto.getNationNum()).and(consumption.state.eq(ConsumptionStateType.IN_USE)))
                .fetch();

        return consumptionList;
    }

    /**
     * 소비 항목 추가
     * */
    public void createConsumption(RequestConsumptionDto dto){
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        Consumption consumption = Consumption.toEntity(nation, dto);
        consumptionRepository.save(consumption);
    }

    /**
     * 소비 항목 수정
     * */
    public long updateConsumption(RequestConsumptionDto dto){
        //Exception 처리
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        //Exception 처리
        Consumption entity = consumptionRepository.findById(dto.getConsumptionNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.CONSUMPTION_NOT_EXIST_EXCEPTION);
        }

        //수정 내역으로 소비 항목 추가
        Consumption updateConsumption = Consumption.toEntity(nation, dto);//content, amount 보내기
        consumptionRepository.save(updateConsumption);

        //소비 기존 내역 상태 수정하기
        QConsumption consumption = QConsumption.consumption;
        long result = queryFactory
                .update(consumption)
                .set(consumption.state, ConsumptionStateType.UN_USE)
                .where(consumption.consumptionNum.eq(dto.getConsumptionNum()))
                .execute();

        return result;
    }

    /**
     * 소비 항목 삭제
     * */
    public long updateConsumptionState(RequestConsumptionDto dto){
        //Exception 처리
        Consumption entity = consumptionRepository.findById(dto.getConsumptionNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.CONSUMPTION_NOT_EXIST_EXCEPTION);
        }

        QConsumption consumption = QConsumption.consumption;
        long result = queryFactory
                .update(consumption)
                .set(consumption.state, ConsumptionStateType.UN_USE)
                .where(consumption.consumptionNum.eq(dto.getConsumptionNum()))
                .execute();

        return  result;
    }

    /**
     * 소비 신청 항목(미결재)조회
     * */
    public List<ResponseConsumptionNationStudentDto> getOutStandingConsumptions(RequestConsumptionNationStudentDto dto){
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QConsumption consumption = QConsumption.consumption;
        QConsumptionNationStudent consumptionNationStudent = QConsumptionNationStudent.consumptionNationStudent;

        List<ResponseConsumptionNationStudentDto> list = queryFactory.select(
                        Projections.constructor(
                                ResponseConsumptionNationStudentDto.class,
                                consumptionNationStudent.consumptionNationStudentNum,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                consumption.content,
                                consumption.amount,
                                consumptionNationStudent.state,
                                consumptionNationStudent.createdDate,
                                consumptionNationStudent.updatedDate
                        )
                )
                .from(consumption)
                .join(consumptionNationStudent).on(consumptionNationStudent.consumption.consumptionNum.eq(consumption.consumptionNum))
                .join(nationStudent).on(consumptionNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                .where(nationStudent.nation.nationNum.eq(dto.getNationNum()).and(consumptionNationStudent.state.eq(ConsumptionStateType.BEFORE_CHECK)))
                .orderBy(consumptionNationStudent.createdDate.desc())
                .fetch();
        System.out.println(list.size());
        return list;
    }

    /**
     * 소비 신청 항목(결재)조회
     * */
    public List<ResponseConsumptionNationStudentDto> getApprovalConsumptions(RequestConsumptionNationStudentDto dto){
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QConsumption consumption = QConsumption.consumption;
        QConsumptionNationStudent consumptionNationStudent = QConsumptionNationStudent.consumptionNationStudent;

        List<ResponseConsumptionNationStudentDto> list = queryFactory.select(
                        Projections.constructor(
                                ResponseConsumptionNationStudentDto.class,
                                consumptionNationStudent.consumptionNationStudentNum,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                consumption.content,
                                consumption.amount,
                                consumptionNationStudent.state,
                                consumptionNationStudent.createdDate,
                                consumptionNationStudent.updatedDate
                        )
                )
                .from(consumption)
                .join(consumptionNationStudent).on(consumptionNationStudent.consumption.consumptionNum.eq(consumption.consumptionNum))
                .join(nationStudent).on(consumptionNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                .where(nationStudent.nation.nationNum.eq(dto.getNationNum()).and(consumptionNationStudent.state.in(ConsumptionStateType.APPROVAL, ConsumptionStateType.REFUSE)))
                .orderBy(consumptionNationStudent.createdDate.desc())
                .fetch();
        return list;
    }

    /**
     * 소비 신청 미결재 처리
     * */
    public long updateConsumptionNationStudentStateByTeacher(RequestConsumptionNationStudentDto dto){
        //Exception 처리
        ConsumptionNationStudent consumptionNationStudentEntity = consumptionNationStudentRepository.findById(dto.getConsumptionNationStudentNum()).orElse(null);
        if(consumptionNationStudentEntity == null){
            throw new ApiException(ExceptionEnum.CONSUMPTION_NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        QConsumptionNationStudent consumptionNationStudent = QConsumptionNationStudent.consumptionNationStudent;
        long result = queryFactory
                .update(consumptionNationStudent)
                .set(consumptionNationStudent.state, dto.getState())
                .where(consumptionNationStudent.consumptionNationStudentNum.eq(dto.getConsumptionNationStudentNum()))
                .execute();

        //승인이면 은행 로직 수행
        if(dto.getState() == ConsumptionStateType.APPROVAL){
            //학생 계좌에서 돈 빼가는 로직 하기
            NationStudent nationStudentEntity = consumptionNationStudentEntity.getNationStudent();
            Consumption consumptionEntity = consumptionNationStudentEntity.getConsumption();

            QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;
            BankNationStudent bankNationStudentEntity = studentBankService.findByNationStudentNum(nationStudentEntity.getNationStudentNum());

            if(bankNationStudentEntity.getBalance() < consumptionEntity.getAmount()){//학생 잔액이 금액보다 적으면
                throw new ApiException(ExceptionEnum.CONSUMPTION_NOT_ENOUGH_AMOUNT_EXCEPTION);
            }else {
                //학생 잔액에서 벌금 빼감
                queryFactory
                        .update(bankNationStudent)
                        .set(bankNationStudent.balance, bankNationStudentEntity.getBalance()-consumptionEntity.getAmount())
                        .where(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudentEntity.getNationStudentNum()))
                        .execute();

                //계좌에 내역 남기기
                String content = consumptionEntity.getContent();
                TransactionHistory transactionHistory = TransactionHistory.of(bankNationStudentEntity, content, BankStateType.CATEGORY_CONSUMPTION, BankStateType.WITHDRAW, (long) consumptionEntity.getAmount());
                transactionHistoryRepository.save(transactionHistory);
            }
        }
        
        return result;

    }

}
