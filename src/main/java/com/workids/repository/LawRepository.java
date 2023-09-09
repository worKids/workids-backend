package com.workids.repository;

import com.workids.domain.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LawRepository extends JpaRepository<Law, Long>{

}
