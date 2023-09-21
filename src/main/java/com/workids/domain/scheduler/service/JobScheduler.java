package com.workids.domain.scheduler.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.BankNationStudent;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.bank.service.StudentBankService;
import com.workids.domain.scheduler.vo.JobSalaryVo;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.config.stateType.JobStateType;
import com.workids.global.config.stateType.NationStateType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.workids.domain.job.entity.QJob.job;
import static com.workids.domain.job.entity.QJobNationStudent.jobNationStudent;
import static com.workids.domain.nation.entity.QNation.nation;

/**
 * 직업 Scheduler
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobScheduler {
    private final StudentBankService studentBankService;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final JPAQueryFactory queryFactory;

    /**
     * 월급 지급 처리 Scheduler
     * 매일 오후 12시 0분에 수행
     */
    @Transactional
    @Scheduled(cron = "0 0 12 * * *", zone = "Asia/Seoul")
    public void createJobSalary(){
        System.out.println("월급 지급 처리 스케줄러 실행");

        // 시간 범위
        LocalDate now = LocalDate.now();
        int nowDay = now.getDayOfMonth();
        int nowMonth = now.getMonthValue();

        // 월급 지급 처리해야 하는 정보
        List<JobSalaryVo> JobSalaryVoList;
        JobSalaryVoList = queryFactory.select(
                            Projections.constructor(
                                    JobSalaryVo.class,
                                    jobNationStudent.nationStudent.nationStudentNum,
                                    job.name.as("jobName"),
                                    job.salary,
                                    nation.taxRate
                            )
                    ).from(jobNationStudent)
                    .join(job).on(jobNationStudent.job.jobNum.eq(job.jobNum))
                    .join(nation).on(job.nation.nationNum.eq(nation.nationNum))
                    .where(nation.payDay.eq(nowDay),
                            nation.state.eq(NationStateType.IN_OPERATE),
                            jobNationStudent.state.eq(JobStateType.EMPLOY))
                    .fetch();

        // 각 시민 월급 지급 처리
        JobSalaryVoList.forEach(b->{
            System.out.println(b);
            Long nationStudentNum = b.getNationStudentNum(); // 나라-학생 고유 번호
            String jobName = b.getJobName(); // 직업명
            int salary = b.getSalary(); // 월급
            int taxRate = b.getTaxRate(); // 세율

            // 실수령액
            long actualSalary = (long)(salary - (salary * ((double) taxRate / 100)));

            // 주거래 계좌
            BankNationStudent mainAccountBankNationStudent = studentBankService.findMainAccountByNationStudentNum(nationStudentNum);

            // 주거래 계좌 입금 transaction 생성-월급
            String content = nowMonth + "월 " + jobName + " 월급";
            TransactionHistory mainTransactionHistory = TransactionHistory.of(mainAccountBankNationStudent, content, BankStateType.CATEGORY_SALARY, BankStateType.DEPOSIT, actualSalary);
            transactionHistoryRepository.save(mainTransactionHistory);

            // 주거래 계좌로 월급만큼 이체
            Long mainAccountBalance = mainAccountBankNationStudent.getBalance(); // 주거래 계좌 잔액
            mainAccountBankNationStudent.updateBalance(mainAccountBalance+actualSalary);
        });
    }
}
