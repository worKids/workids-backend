package com.workids.domain.scheduler.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.nation.entity.Nation;
import com.workids.global.config.stateType.NationStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.workids.domain.nation.entity.QNation.nation;

/**
 * 나라 Scheduler
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationScheduler {
    private final JPAQueryFactory queryFactory;

    /**
     * 나라 운영 시작 처리 Scheduler
     * 매일 오전 12시 0분에 수행
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void updateNationOperateStart(){
        System.out.println("나라 운영 시작 처리 스케줄러 실행");

        // 시간 범위
        LocalDate now = LocalDate.now();
        LocalDateTime nowStart = now.atStartOfDay();
        LocalDateTime nowEnd = LocalDateTime.of(now, LocalTime.MAX).withNano(0);

        // 나라 운영 시작 처리해야 하는 나라 정보
        List<Nation> nationList;
        nationList = queryFactory.selectFrom(nation)
                .where(nation.state.eq(NationStateType.BEFORE_OPERATE),
                        nation.startDate.between(nowStart, nowEnd))
                .fetch();

        // 나라 상태 업데이트
        nationList.forEach((b)->{
            System.out.println(b);
            b.updateOperateState(NationStateType.IN_OPERATE);
        });
    }

    /**
     * 나라 운영 종료 처리 Scheduler
     * 매일 오전 12시 30분에 수행
     */
    @Transactional
    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
    public void updateNationOperateEnd(){
        System.out.println("나라 운영 종료 처리 스케줄러 실행");

        // 시간 범위
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime yesterdayStart = yesterday.atStartOfDay();
        LocalDateTime yesterdayEnd = LocalDateTime.of(yesterday, LocalTime.MAX).withNano(0);

        // 나라 운영 종료 처리해야 하는 나라 정보
        List<Nation> nationList;
        nationList = queryFactory.selectFrom(nation)
                .where(nation.state.eq(NationStateType.IN_OPERATE),
                        nation.endDate.between(yesterdayStart, yesterdayEnd))
                .fetch();

        // 나라 상태 업데이트
        nationList.forEach((b)->{
            System.out.println(b);
            b.updateOperateState(NationStateType.END_OPERATE);
        });
    }
}
