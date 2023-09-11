package com.workids.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Configuration //환경설정을 돕는 선언
public class AppConfig {

    @PersistenceContext //EntityManager는 각 석비스마다 새롭 생성해서 주입
    private EntityManager em;

    @Bean //<bean
    public JPAQueryFactory getQueryFactory() {
        System.out.println("em = " + em);
        return new JPAQueryFactory(em);
    }
}