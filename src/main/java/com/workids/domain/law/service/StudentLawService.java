package com.workids.domain.law.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.law.dto.request.RequestLawNationStudentDto;
import com.workids.domain.law.dto.response.ResponseLawNationStudentDto;
import com.workids.domain.law.entity.QLaw;
import com.workids.domain.law.entity.QLawNationStudent;
import com.workids.domain.nation.entity.QNationStudent;
import com.workids.global.config.stateType.LawStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentLawService {

    private final JPAQueryFactory queryFactory;

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
                                law.type,
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState,
                                lawNationStudent.createdDate,
                                lawNationStudent.updatedDate
                        )
                )
                .from(law)
                .join(lawNationStudent).on(law.lawNum.eq(lawNationStudent.law.lawNum))
                .join(nationStudent).on(lawNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                .where(nationStudent.nationStudentNum.eq(dto.getNationStudentNum()).and(law.type.eq(LawStateType.FINE)))
                .orderBy(lawNationStudent.updatedDate.desc())
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
                                law.type,
                                law.fine,
                                law.penalty,
                                lawNationStudent.penaltyCompleteState,
                                lawNationStudent.createdDate,
                                lawNationStudent.updatedDate
                        )
                )
                .from(law)
                .join(lawNationStudent).on(law.lawNum.eq(lawNationStudent.law.lawNum))
                .join(nationStudent).on(lawNationStudent.nationStudent.nationStudentNum.eq(nationStudent.nationStudentNum))
                .where(nationStudent.nationStudentNum.eq(dto.getNationStudentNum()).and(law.type.eq(LawStateType.PENALTY)))
                .orderBy(lawNationStudent.updatedDate.desc())
                .fetch();

        return studentPenaltyList;
    }
}
