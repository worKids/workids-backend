package com.workids.domain.law.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.law.dto.request.RequestLawDto;
import com.workids.domain.law.dto.request.RequestLawNationStudentDto;
import com.workids.domain.law.dto.response.ResponseLawDto;
import com.workids.domain.law.dto.response.ResponseLawNationStudentDto;
import com.workids.domain.law.entity.Law;
import com.workids.domain.law.entity.LawNationStudent;
import com.workids.domain.law.entity.QLaw;
import com.workids.domain.law.entity.QLawNationStudent;
import com.workids.domain.law.repository.LawNationStudentRepository;
import com.workids.domain.law.repository.LawRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.global.config.stateType.LawStateType;
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
public class TeacherLawService {

    @Autowired
    private LawRepository lawRepository;

    @Autowired
    private LawNationStudentRepository lawNationStudentRepository;

    @Autowired
    private NationStudentRepository nationStudentRepository;

    @Autowired
    private NationRepository nationRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 법 내역 조회
     * */
    public List<ResponseLawDto> getAllLaws(RequestLawDto dto){
        //Exception 처리
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        QLaw law = QLaw.law;

        List<ResponseLawDto> lawList = queryFactory.select(
                        Projections.constructor(
                                ResponseLawDto.class,
                                law.lawNum,
                                law.content,
                                law.type,
                                law.fine,
                                law.penalty,
                                law.createdDate
                        )
                )
                .from(law)
                .where(law.nation.nationNum.eq(dto.getNationNum()).and(law.state.eq(LawStateType.IN_USE)))
                .fetch();

        return lawList;
    }

    /**
     * 법 등록
     * */
    public void createLaw(RequestLawDto dto){
        //Exception 처리
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));

        Law law = Law.toEntity(nation, dto);
        lawRepository.save(law);
    }

    /**
     * 법 수정(벌금 가격만 가능)
     * */
    public long updateLaw(RequestLawDto dto) {
        //Exception 처리
        Nation nation = nationRepository.findById(dto.getNationNum())
                .orElseThrow(()->new ApiException(ExceptionEnum.NATION_NOT_EXIST_EXCEPTION));
        //Exception 처리
        Law entity = lawRepository.findById(dto.getLawNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.LAW_NOT_EXIST_EXCEPTION);
        }

        //수정 내역으로 법 항목 추가
        Law updateLaw = Law.toEntity(nation,dto);
        lawRepository.save(updateLaw);
        
        //법 기존 내역 상태 수정하기
        QLaw law = QLaw.law;
        long reuslt = queryFactory
                .update(law)
                .set(law.state, LawStateType.UN_USE)
                .where(law.lawNum.eq(dto.getLawNum()))
                .execute();

        return reuslt;
    }

    /**
     * 법 삭제
     * */
    public long updateLawState(RequestLawDto dto){
        //Exception 처리
        Law entity = lawRepository.findById(dto.getLawNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.LAW_NOT_EXIST_EXCEPTION);
        }

        QLaw law = QLaw.law;

        long result = queryFactory
                .update(law)
                .set(law.state, LawStateType.UN_USE)
                .where(law.lawNum.eq(dto.getLawNum()))
                .execute();
        return result;
    }

    /**
     * 벌금부여 리스트
     */
    public List<ResponseLawNationStudentDto> getFineLaws(RequestLawNationStudentDto dto){
        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QLaw law = QLaw.law;


        List<ResponseLawNationStudentDto> fineList = queryFactory.select(
                        Projections.constructor(
                                ResponseLawNationStudentDto.class,
                                lawNationStudent.lawNationStudentNum,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                law.content,
                                law.type,
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState,
                                lawNationStudent.createdDate,
                                lawNationStudent.updatedDate
                        )
                )
                .from(lawNationStudent)
                .join(law).on(lawNationStudent.law.lawNum.eq(law.lawNum))
                .join(nationStudent).on(law.nation.nationNum.eq(nationStudent.nation.nationNum))
                .where(nationStudent.nation.nationNum.eq(dto.getNationNum()).and(law.type.eq(LawStateType.FINE)))
                .orderBy(lawNationStudent.createdDate.desc())
                .fetch();

        return fineList;
    };

    /**
     * 벌금 부여
     * */
    public void createFineStudent(RequestLawNationStudentDto dto){
        //Exception 처리
        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        if(nationStudent == null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }
        //Exception 처리
        Law law = lawRepository.findById(dto.getLawNum()).orElse(null);
        if(law == null){
            throw new ApiException(ExceptionEnum.LAW_NOT_EXIST_EXCEPTION);
        }

        LawNationStudent lawNationStudent = LawNationStudent.toEntity(law, nationStudent,dto);
        lawNationStudentRepository.save(lawNationStudent);

        //벌금 빼가는 작동 추가적으로 구현

    };

    /**
     * 벌금 부여 취소
     * */
    public void deleteFineStudent(RequestLawNationStudentDto dto){
        //Exception 처리
        LawNationStudent entity = lawNationStudentRepository.findById(dto.getLawNationStudentNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.LAW_NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        //벌금 다시 되돌리는 작동 추가적으로 구현

        lawNationStudentRepository.deleteById(dto.getLawNationStudentNum());
    };

    /**
     * 벌칙 부여 리스트
     */
    public List<ResponseLawNationStudentDto> getPenaltyLaws(RequestLawNationStudentDto dto){

        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QLaw law = QLaw.law;


        List<ResponseLawNationStudentDto> penaltyList = queryFactory.select(
                        Projections.constructor(
                                ResponseLawNationStudentDto.class,
                                lawNationStudent.lawNationStudentNum,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                law.content,
                                law.type,
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState,
                                lawNationStudent.createdDate,
                                lawNationStudent.updatedDate
                        )
                )
                .from(lawNationStudent)
                .join(law).on(lawNationStudent.law.lawNum.eq(law.lawNum))
                .join(nationStudent).on(law.nation.nationNum.eq(nationStudent.nation.nationNum))
                .where(nationStudent.nation.nationNum.eq(dto.getNationNum()).and(law.type.eq(LawStateType.PENALTY)))
                .orderBy(lawNationStudent.createdDate.desc())
                .fetch();

        return penaltyList;
    };

    /**
     * 벌칙 부여
     * */
    public void createPenaltyStudent(RequestLawNationStudentDto dto){
        //Exception 처리
        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        if(nationStudent == null){
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }
        //Exception 처리
        Law law = lawRepository.findById(dto.getLawNum()).orElse(null);
        if(law == null){
            throw new ApiException(ExceptionEnum.LAW_NOT_EXIST_EXCEPTION);
        }

        LawNationStudent lawNationStudent = LawNationStudent.toEntity(law, nationStudent,dto);
        lawNationStudentRepository.save(lawNationStudent);
    };

    /**
     * 벌칙 부여 취소
     * */
    public void deletePenaltyStudent(RequestLawNationStudentDto dto){
        //Exception 처리
        LawNationStudent entity = lawNationStudentRepository.findById(dto.getLawNationStudentNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.LAW_NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        lawNationStudentRepository.deleteById(dto.getLawNationStudentNum());
    };

    /**
     * 벌칙 수행 확인여부
     * */
    public long updatePenaltyCompleteState(RequestLawNationStudentDto dto){
        //Exception 처리
        LawNationStudent entity = lawNationStudentRepository.findById(dto.getLawNationStudentNum()).orElse(null);
        if(entity == null){
            throw new ApiException(ExceptionEnum.LAW_NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }

        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;

        long result = queryFactory
                .update(lawNationStudent)
                .set(lawNationStudent.penaltyCompleteState, LawStateType.PENALTY_COMPLETE)
                .where(lawNationStudent.lawNationStudentNum.eq(dto.getLawNationStudentNum()))
                .execute();

        return result;
    };
}
