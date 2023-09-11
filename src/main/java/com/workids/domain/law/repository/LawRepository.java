package com.workids.domain.law.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.law.dto.request.RequestLawDto;
import com.workids.domain.law.entity.Law;
import com.workids.domain.law.entity.QLaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface LawRepository extends JpaRepository<Law, Long>, QuerydslPredicateExecutor<Law> {

    /**
     * 나라 법 조회
     * */
    /*@Query("select new com.workids.dto.response.ResponseLawDto (l.lawNum, l.content, l.type, l.fine, l.penalty) from Law l where l.nation.nationNum = ?1 and l.state=0")
    List<Law> findByNation(RequestLawDto dto);*/

    /**
     * 나라법 수정(벌금만 수정)
     * */
    /*@Query("update Law l set l.fine=?2 where l.lawNum=?1")
    @Modifying
    int updateFineByLawNum(Long lawNum, int fine);*/

    /**
     * 나라법 삭제(상태 수정)
     * */
    /*@Query("update Law l set l.state=1 where l.lawNum=?1")
    @Modifying
    int updateStateByLawNum(Long lawNum);*/



}
