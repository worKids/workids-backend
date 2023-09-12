package com.workids.domain.consumption.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.NationRepository;
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
    @Transactional
    public void createConsumption(RequestConsumptionDto dto){
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        Consumption consumption = Consumption.toEntity(nation, dto);
        consumptionRepository.save(consumption);
    }

    /**
     * 소비 항목 수정
     * */
    @Transactional
    public long updateConsumption(RequestConsumptionDto dto){
        Consumption entity = consumptionRepository.findById(dto.getConsumptionNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.CONSUMPTION_NOT_EXIST_EXCEPTION);
        }

        QConsumption consumption = QConsumption.consumption;
        long result = queryFactory
                .update(consumption)
                .set(consumption.amount, dto.getAmount())
                .where(consumption.consumptionNum.eq(dto.getConsumptionNum()))
                .execute();
        return result;
    }

    /**
     * 소비 항목 삭제
     * */
    @Transactional
    public long updateConsumptionState(RequestConsumptionDto dto){
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
    @Transactional
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
                                consumptionNationStudent.createdDate
                        )
                )
                .from(consumptionNationStudent)
                .join(consumption).on(consumptionNationStudent.consumption.consumptionNum.eq(consumption.consumptionNum))
                .join(nationStudent).on(consumption.nation.nationNum.eq(nationStudent.nation.nationNum))
                .where(nationStudent.nation.nationNum.eq(dto.getNationNum()).and(consumptionNationStudent.state.eq(ConsumptionStateType.BEFORE_CHECK)))
                .orderBy(consumptionNationStudent.createdDate.desc())
                .fetch();
        return list;
    }

    /**
     * 소비 신청 항목(결재)조회
     * */
    @Transactional
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
                                consumptionNationStudent.createdDate
                        )
                )
                .from(consumptionNationStudent)
                .join(consumption).on(consumptionNationStudent.consumption.consumptionNum.eq(consumption.consumptionNum))
                .join(nationStudent).on(consumption.nation.nationNum.eq(nationStudent.nation.nationNum))
                .where(nationStudent.nation.nationNum.eq(dto.getNationNum()).and(consumptionNationStudent.state.in(ConsumptionStateType.APPROVAL, ConsumptionStateType.REFUSE)))
                .orderBy(consumptionNationStudent.createdDate.desc())
                .fetch();
        return list;
    }

    /**
     * 소비 신청 미결재 처리
     * */
    @Transactional
    public long updateConsumptionNationStudentStateByTeacher(RequestConsumptionNationStudentDto dto){
        ConsumptionNationStudent entity = consumptionNationStudentRepository.findById(dto.getConsumptionNationStudentNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.CONSUMPTION_NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        QConsumptionNationStudent consumptionNationStudent = QConsumptionNationStudent.consumptionNationStudent;
        long result = queryFactory
                .update(consumptionNationStudent)
                .set(consumptionNationStudent.state, dto.getState())
                .where(consumptionNationStudent.consumptionNationStudentNum.eq(dto.getConsumptionNationStudentNum()))
                .execute();
        
        if(dto.getState() == ConsumptionStateType.APPROVAL){
            //학생 계좌에서 돈 빼가는 로직 하기
        }
        
        return result;

    }

}
