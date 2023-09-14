package com.workids.domain.law.repository;

import com.workids.domain.law.dto.response.ResponseLawNationStudentDto;
import com.workids.domain.law.entity.LawNationStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface LawNationStudentRepository extends JpaRepository<LawNationStudent, Long>, QuerydslPredicateExecutor<LawNationStudent> {

    /**
     * 벌금 부여 리스트
     * */
    /*@Query("select new com.workids.domain.law.dto.response.ResponseLawNationStudentDto(b.lawNationStudentNum, c.citizenNumber, c.studentName, a.content, a.type,a.fine, a.penalty, b.penaltyCompleteState,b.createdDate, b.updatedDate) " +
            "FROM Law a join LawNationStudent b on a.lawNum = b.law.lawNum " +
            "join NationStudent c on b.nationStudent.nationStudentNum = c.nationStudentNum where c.nation.nationNum=?1 and a.type=0 order by a.createdDate desc")
    List<ResponseLawNationStudentDto> findFineByNation(long nationNum);*/

    /**
     * 벌칙 부여 리스트
     * */
    /*@Query("select new com.workids.domain.law.dto.response.ResponseLawNationStudentDto(b.lawNationStudentNum, c.citizenNumber, c.studentName, a.content, a.type,a.fine, a.penalty, b.penaltyCompleteState,b.createdDate, b.updatedDate) " +
            "FROM Law a join LawNationStudent b on a.lawNum = b.law.lawNum " +
            "join NationStudent c on b.nationStudent.nationStudentNum = c.nationStudentNum where c.nation.nationNum=?1 and b.type=1 order by a.createDate desc")
    List<ResponseLawNationStudentDto> findPenaltyByNation(long nationNum);*/

    /**
     * 벌칙 수행 체크
     * */
    /*@Query("update LawNationStudent  l set l.penaltyCompleteState=1 where l.lawNationStudentNum = ?1 ")
    @Modifying
    int updateStateByLawNationStudentNum(long lawNationStudentNum);*/

    /**
     * 벌금 부여 리스트
     * */
    /*@Query("select new com.workids.domain.law.dto.response.ResponseLawNationStudentDto(b.lawNationStudentNum, c.citizenNumber, c.studentName, a.content, a.type,a.fine, a.penalty, b.penaltyCompleteState,b.createdDate, b.updatedDate) " +
            "FROM Law a join LawNationStudent b on a.lawNum = b.law.lawNum " +
            "join NationStudent c on b.nationStudent.nationStudentNum = c.nationStudentNum  where c.nationStudentNum = ?1 and b.type=0 order by a.createDate desc")
    List<ResponseLawNationStudentDto> findStudentFine(long nationStudentNum);*/

    /**
     * 벌칙 부여 리스트
     * */
    /*@Query("select new com.workids.domain.law.dto.response.ResponseLawNationStudentDto(b.lawNationStudentNum, c.citizenNumber, c.studentName, a.content, a.type,a.fine, a.penalty, b.penaltyCompleteState,b.createdDate, b.updatedDate) " +
            "FROM Law a join LawNationStudent b on a.lawNum = b.law.lawNum " +
            "join NationStudent c on b.nationStudent.nationStudentNum = c.nationStudentNum  where c.nationStudentNum = ?1 and b.type=1 order by a.createDate desc")
    List<ResponseLawNationStudentDto> findStudentPenalty(long nationStudentNum);*/


}
