package com.workids.repository;

import com.workids.domain.LawNationStudent;
import com.workids.dto.response.ResponseLawNationStudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface LawNationStudentRepository extends JpaRepository<LawNationStudent, Long>, QuerydslPredicateExecutor<LawNationStudent> {

    /**
     * 벌금 부여 리스트
     * */
    /*@Query("select new com.workids.dto.response.ResponseLawNationStudentDto(a.lawNationStudentNum, c.citizenNumber, c.studentName, b.content, b.fine, b.penalty, a.penaltyCompleteState) " +
            "FROM LawNationStudent a join Law b on a.law.lawNum = b.lawNum " +
            "join NationStudent c on b.nation.nationNum = c.nation.nationNum where c.nation.nationNum=?1 and b.type=0 order by a.createDate desc")
    List<ResponseLawNationStudentDto> findFineByNation(long nationNum);*/

    /**
     * 벌칙 부여 리스트
     * */
    /*@Query("select new com.workids.dto.response.ResponseLawNationStudentDto(a.lawNationStudentNum, c.citizenNumber, c.studentName, b.content, b.fine, b.penalty, a.penaltyCompleteState) " +
            "FROM LawNationStudent a join Law b on a.law.lawNum = b.lawNum " +
            "join NationStudent c on b.nation.nationNum = c.nation.nationNum where c.nation.nationNum=?1 and b.type=1 order by a.createDate desc")
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
    /*@Query("select new com.workids.dto.response.ResponseLawNationStudentDto(a.lawNationStudentNum, c.citizenNumber, c.studentName, b.content, b.fine, b.penalty, a.penaltyCompleteState) " +
            "FROM LawNationStudent a join Law b on a.law.lawNum = b.lawNum " +
            "join NationStudent c on b.nation.nationNum = c.nation.nationNum where c.nationStudentNum = ?1 and b.type=0 order by a.createDate desc")
    List<ResponseLawNationStudentDto> findStudentFine(long nationStudentNum);*/

    /**
     * 벌칙 부여 리스트
     * */
    /*@Query("select new com.workids.dto.response.ResponseLawNationStudentDto(a.lawNationStudentNum, c.citizenNumber, c.studentName, b.content, b.fine, b.penalty, a.penaltyCompleteState) " +
            "FROM LawNationStudent a join Law b on a.law.lawNum = b.lawNum " +
            "join NationStudent c on b.nation.nationNum = c.nation.nationNum where c.nationStudentNum = ?1 and b.type=1 order by a.createDate desc")
    List<ResponseLawNationStudentDto> findStudentPenalty(long nationStudentNum);*/


}
