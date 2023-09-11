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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LawService {

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
    @Transactional
    public List<ResponseLawDto> getAllLaws(RequestLawDto dto){

        QLaw law = QLaw.law;

        List<ResponseLawDto> lawList = queryFactory.select(
                        Projections.constructor(
                                ResponseLawDto.class,
                                law.lawNum,
                                law.content,
                                law.type,
                                law.fine,
                                law.penalty
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
    @Transactional
    public void createLaw(RequestLawDto dto){
        Nation nation = nationRepository.findById(dto.getNationNum()).orElse(null);

        Law law = Law.toEntity(nation, dto);
        lawRepository.save(law);
    }

    /**
     * 법 수정(벌금 가격만 가능)
     * */
    @Transactional
    public long updateLaw(RequestLawDto dto) {
        QLaw law = QLaw.law;
        long reuslt = queryFactory
                .update(law)
                .set(law.fine, dto.getFine())
                .where(law.lawNum.eq(dto.getLawNum()))
                .execute();
        return reuslt;
    }

    /**
     * 법 삭제
     * */
    @Transactional
    public long updateLawState(RequestLawDto dto){
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
    @Transactional
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
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState
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
    @Transactional
    public void createFineStudent(RequestLawNationStudentDto dto){

        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        Law law = lawRepository.findById(dto.getLawNum()).orElse(null);

        LawNationStudent lawNationStudent = LawNationStudent.toEntity(law, nationStudent,dto);
        lawNationStudentRepository.save(lawNationStudent);

        //벌금 빼가는 작동 추가적으로 구현

    };

    /**
     * 벌금 부여 취소
     * */
    @Transactional
    public void deleteFineStudent(RequestLawNationStudentDto dto){

        //벌금 다시 되돌리는 작동 추가적으로 구현

        lawNationStudentRepository.deleteById(dto.getLawNationStudentNum());
    };

    /**
     * 벌칙 부여 리스트
     */
    @Transactional
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
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState
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
    @Transactional
    public void createPenaltyStudent(RequestLawNationStudentDto dto){
        NationStudent nationStudent = nationStudentRepository.findByCitizenNumber(dto.getCitizenNumber());
        Law law = lawRepository.findById(dto.getLawNum()).orElse(null);

        LawNationStudent lawNationStudent = LawNationStudent.toEntity(law, nationStudent,dto);
        lawNationStudentRepository.save(lawNationStudent);
    };

    /**
     * 벌칙 부여 취소
     * */
    public void deletePenaltyStudent(RequestLawNationStudentDto dto){
        lawNationStudentRepository.deleteById(dto.getLawNationStudentNum());
    };

    /**
     * 벌칙 수행 확인여부
     * */
    public long updatePenaltyCompleteState(RequestLawNationStudentDto dto){
        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;

        long result = queryFactory
                .update(lawNationStudent)
                .set(lawNationStudent.penaltyCompleteState, LawStateType.PENALTY_COMPLETE)
                .where(lawNationStudent.lawNationStudentNum.eq(dto.getLawNationStudentNum()))
                .execute();

        return result;
    };

    /**
     * 학생에게 부여된 벌금 내역 조회
     * */
    public List<ResponseLawNationStudentDto> getStudentFineLaws(RequestLawNationStudentDto dto){

        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QLaw law = QLaw.law;

        List<ResponseLawNationStudentDto> studentFineList = queryFactory.select(
                        Projections.constructor(
                                ResponseLawNationStudentDto.class,
                                lawNationStudent.lawNationStudentNum,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                law.content,
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState
                        )
                )
                .from(lawNationStudent)
                .join(law).on(lawNationStudent.law.lawNum.eq(law.lawNum))
                .join(nationStudent).on(law.nation.nationNum.eq(nationStudent.nation.nationNum))
                .where(nationStudent.nationStudentNum.eq(dto.getNationStudentNum()).and(law.type.eq(LawStateType.FINE)))
                .orderBy(lawNationStudent.createdDate.desc())
                .fetch();

        return studentFineList;
    }
    /**
     * 학생에게 부여된 벌칙 내역 조회
     * */
    public List<ResponseLawNationStudentDto>  getStudentPenaltyLaws(RequestLawNationStudentDto dto){
        QLawNationStudent lawNationStudent = QLawNationStudent.lawNationStudent;
        QNationStudent nationStudent = QNationStudent.nationStudent;
        QLaw law = QLaw.law;

        List<ResponseLawNationStudentDto> studentPenaltyList = queryFactory.select(
                        Projections.constructor(
                                ResponseLawNationStudentDto.class,
                                lawNationStudent.lawNationStudentNum,
                                nationStudent.citizenNumber,
                                nationStudent.studentName,
                                law.content,
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState
                        )
                )
                .from(lawNationStudent)
                .join(law).on(lawNationStudent.law.lawNum.eq(law.lawNum))
                .join(nationStudent).on(law.nation.nationNum.eq(nationStudent.nation.nationNum))
                .where(nationStudent.nationStudentNum.eq(dto.getNationStudentNum()).and(law.type.eq(LawStateType.PENALTY)))
                .orderBy(lawNationStudent.createdDate.desc())
                .fetch();

        return studentPenaltyList;
    }
}
