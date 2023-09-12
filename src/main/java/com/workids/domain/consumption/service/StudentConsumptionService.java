package com.workids.domain.consumption.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.consumption.dto.request.RequestConsumptionNationStudentDto;
import com.workids.domain.consumption.dto.response.ResponseConsumptionNationStudentDto;
import com.workids.domain.consumption.entity.Consumption;
import com.workids.domain.consumption.entity.ConsumptionNationStudent;
import com.workids.domain.consumption.entity.QConsumption;
import com.workids.domain.consumption.entity.QConsumptionNationStudent;
import com.workids.domain.consumption.repository.ConsumptionNationStudentRepository;
import com.workids.domain.consumption.repository.ConsumptionRepository;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.ConsumptionStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentConsumptionService {

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private ConsumptionNationStudentRepository consumptionNationStudentRepository;

    @Autowired
    private NationStudentRepository nationStudentRepository;

    private final JPAQueryFactory queryFactory;
    /**
     * 소비 신청
     * */
    public void createStudentConsumption(@RequestBody RequestConsumptionNationStudentDto dto){

        Consumption consumption = consumptionRepository.findById(dto.getConsumptionNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.CONSUMPTION_NOT_EXIST_EXCEPTION));

        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        if(nationStudent == null){
            throw new ApiException(ExceptionEnum.CONSUMPTION_NOT_EXIST_EXCEPTION);
        }

        ConsumptionNationStudent consumptionNationStudent = ConsumptionNationStudent.toEntity(consumption, nationStudent);
        consumptionNationStudentRepository.save(consumptionNationStudent);
    }

    /**
     * 내 소비 신청 내역 조회(대기, 취소, 승인 거절)
     * */
    public List<ResponseConsumptionNationStudentDto> getStudentConsumptions(RequestConsumptionNationStudentDto dto){
        NationStudent entity = nationStudentRepository.findById(dto.getNationStudentNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

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
                .where(nationStudent.nationStudentNum.eq(dto.getNationStudentNum()))
                .orderBy(consumptionNationStudent.createdDate.desc())
                .fetch();
        return list;
    }

    /**
     * 신청한 내역 취소하기 (대기 중인 것만 가능)
     * */
    @Transactional
    public long updateConsumptionNationStudentStateByStudent(RequestConsumptionNationStudentDto dto){
        QConsumptionNationStudent consumptionNationStudent = QConsumptionNationStudent.consumptionNationStudent;

        long result = queryFactory
                .update(consumptionNationStudent)
                .set(consumptionNationStudent.state, ConsumptionStateType.CANCEL)
                .where(consumptionNationStudent.consumptionNationStudentNum.eq(dto.getConsumptionNationStudentNum()))
                .execute();

        return result;
    }

    /**
     * 소비 승인 완료 내역 조회(승인)
     * */
    public List<ResponseConsumptionNationStudentDto> getCompleteStudentConsumptions(RequestConsumptionNationStudentDto dto){

        NationStudent entity = nationStudentRepository.findById(dto.getNationStudentNum()).orElse(null);

        if(entity == null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }
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
                .where(nationStudent.nationStudentNum.eq(dto.getNationStudentNum()).and(consumptionNationStudent.state.eq(ConsumptionStateType.APPROVAL)))
                .orderBy(consumptionNationStudent.createdDate.desc())
                .fetch();
        return list;
    }

}
