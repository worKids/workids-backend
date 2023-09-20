package com.workids.domain.statistic.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.workids.domain.bank.entity.QBank;
import com.workids.domain.bank.entity.QBankNationStudent;
import com.workids.domain.bank.entity.QTransactionHistory;
import com.workids.domain.bank.entity.TransactionHistory;
import com.workids.domain.bank.repository.BankNationStudentRepository;
import com.workids.domain.bank.repository.TransactionHistoryRepository;
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.domain.nation.service.NationService;
import com.workids.domain.statistic.dto.MenuValue;
import com.workids.domain.statistic.dto.request.RequestStatisticDto;
import com.workids.domain.statistic.dto.response.*;
import com.workids.global.config.stateType.BankStateType;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticService {

    private final NationStudentRepository nationStudentRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final NationRepository nationRepository;
    private final JPAQueryFactory queryFactory;

    /**
     * 학생 통계 조회
     * @param dto
     * @return
     */
    public ResponseStatisticDto getStatistic(RequestStatisticDto dto) {
        System.out.println("dto.getNationStudentNum() = " + dto.getNationStudentNum());
        NationStudent student = nationStudentRepository.findByNationStudentNum(dto.getNationStudentNum());
        if (student == null) {
            throw new ApiException(ExceptionEnum.NATION_STUDENT_NOT_EXIST_EXCEPTION);
        }
        // 나중에는 수익, 지출 여기서 조회 후 아래로 내려주게 바꾸기
        ResponseAssetDto assetDto = getAsset(dto);
        ResponseExpendDto expendDto = getExpend(dto);
        ResponseIncomeDto incomeDto = getIncome(dto);
        ResponseIncomeExpendDto incomeExpendDto = getIncomeExpend(dto);
        ResponseMonthlyStatisticDto monthlyDto = getMonthly(dto);
        return ResponseStatisticDto.toDto(assetDto, expendDto, incomeDto,incomeExpendDto,monthlyDto);
    }
    /**
     * 현금 - 예금 조회
     * @param dto
     * @return
     */
    public ResponseAssetDto getAsset(RequestStatisticDto dto) {

        List<String> assetMenu = getAssetDetail(dto.getNationStudentNum());
        List<Integer> assetPercent = getAssetPercent(dto.getNationStudentNum());
        return ResponseAssetDto.toDto(assetMenu, assetPercent);

    }

    /**
     * 소비 통계 조회
     * @param dto
     * @return
     */
    public ResponseExpendDto getExpend(RequestStatisticDto dto) {
        List<MenuValue> list = getExpendDetail(dto.getNationStudentNum());
        if (list.isEmpty()) {
            return null;
        }
        List<String> expendMenu = new ArrayList<>();
        List<Integer> expendPercent = new ArrayList<>();
        Long totalSum = 0L;
        for (MenuValue mv : list) {
            totalSum += mv.getValue();
        }

        for (MenuValue mv : list) {
            expendMenu.add(mv.getMenu());
            expendPercent.add(Math.round((float) mv.getValue()/totalSum * 100));
        }
        return ResponseExpendDto.toDto(expendMenu, expendPercent);
    }

    /**
     * 수입 통계 조회
     * @param dto
     * @return
     */
    public ResponseIncomeDto getIncome(RequestStatisticDto dto) {
        List<MenuValue> list = getIncomeDetail(dto.getNationStudentNum());
        if (list.isEmpty()) {
            return null;
        }
        List<String> incomeMenu = new ArrayList<>();
        List<Integer> incomePercent = new ArrayList<>();
        Long totalSum = 0L;
        for (MenuValue mv : list) {
            totalSum += mv.getValue();
        }

        for (MenuValue mv : list) {
            incomeMenu.add(mv.getMenu());
            incomePercent.add(Math.round((float) mv.getValue()/totalSum * 100));
        }
        return ResponseIncomeDto.toDto(incomeMenu, incomePercent);
    }

    /**
     * 수익 지출 비율 조회
     * @param dto
     * @return
     */
    public ResponseIncomeExpendDto getIncomeExpend(RequestStatisticDto dto) {

        List<String> incomeExpendMenu = new ArrayList<>();
        List<Integer> incomeExpendPercent = new ArrayList<>();
        Long expendTotalSum;
        Long incomeTotalSum;
        if (getTotalIncome(dto.getNationStudentNum()) == null) {
            incomeTotalSum = 0L;
        } else {
            incomeTotalSum = getTotalIncome(dto.getNationStudentNum());
        }
         if (getTotalexpend(dto.getNationStudentNum()) == null) {
             expendTotalSum = 0L;
         } else {
             expendTotalSum = getTotalexpend(dto.getNationStudentNum());
         }
        System.out.println("incomeTotalSum = " + incomeTotalSum);
        System.out.println("expendTotalSum = " + expendTotalSum);

        if (expendTotalSum == 0L && incomeTotalSum == 0L) {
            return null;
        }
        Long totalSum = expendTotalSum + incomeTotalSum;
        incomeExpendMenu.add("수입");
        incomeExpendMenu.add("지출");
        incomeExpendPercent.add(Math.round((float) incomeTotalSum/totalSum * 100));
        incomeExpendPercent.add(Math.round((float) expendTotalSum/totalSum * 100));

        return ResponseIncomeExpendDto.toDto(incomeExpendMenu, incomeExpendPercent);
    }

    /**
     * 월별 수입, 지출 조회
     * @param dto
     * @return
     */
    public ResponseMonthlyStatisticDto getMonthly(RequestStatisticDto dto) {
        Long bankStudentNum = getBankStudentNum(dto.getNationStudentNum());

        // 전체 히스토리 조회
        List<TransactionHistory> list = transactionHistoryRepository
                .findAllByBankNationStudent_BankNationStudentNum(bankStudentNum);

        // 달 조회
        List<YearMonth> yearMonthList = getNationMonth(dto.getNationNum());
        Map<YearMonth, Long> incomeMap = new HashMap<>();
        Map<YearMonth, Long> expendMap = new HashMap<>();

        for (int i = 0; i<yearMonthList.size(); i++) {
            incomeMap.put(yearMonthList.get(i), 0L);
            expendMap.put(yearMonthList.get(i), 0L);
        }
        for (TransactionHistory t : list) {
            if (t.getType()==0 && !t.getCategory().equals("가입")) {
                YearMonth current = YearMonth.from(t.getTransactionDate());
                Long amount = incomeMap.get(current) + t.getAmount();
                incomeMap.put(current, amount);
            } else if (t.getType()==1 && !t.getCategory().equals("이체")) {
                YearMonth current = YearMonth.from(t.getTransactionDate());
                Long amount = expendMap.get(current) + t.getAmount();
                expendMap.put(current, amount);
            }
        }
        List<Long> incomeList = new ArrayList<>(incomeMap.values());
        List<Long> expendList = new ArrayList<>(expendMap.values());
        List<YearMonth> monthList = new ArrayList<>(expendMap.keySet());

        System.out.println("monthList = " + monthList);
        System.out.println("incomeList = " + incomeList);
        System.out.println("expendList = " + expendList);
        return ResponseMonthlyStatisticDto.toDto(monthList, incomeList, expendList);
    }
    // querydsl 용

    /**
     * 개인 은행 계좌 조회
     * @param studentNum
     * @return
     */
    private List<String> getAssetDetail(Long studentNum) {
        List<String> result = new ArrayList<>();
        QBankNationStudent student = QBankNationStudent.bankNationStudent;
        List<Integer> list = queryFactory.select(student.bank.productType)
                .from(student)
                .where(student.nationStudent.nationStudentNum.eq(studentNum))
                .groupBy(student.bank.productType)
                .fetch();
        for (int i : list) {
            if (i==0) {
                result.add("현금");
            } else if (i==1) {
                result.add("예금");
            }
        }
        return result;
    }

    /**
     * 자산 비율 조회
     * @param studentNum
     * @return
     */
    private List<Integer> getAssetPercent(Long studentNum) {
        List<Integer> result = new ArrayList<>();
        QBankNationStudent student = QBankNationStudent.bankNationStudent;
        List<Long> list = queryFactory.select(student.balance.sum())
                .from(student)
                .where(student.nationStudent.nationStudentNum.eq(studentNum))
                .groupBy(student.bank.productType)
                .fetch();
        System.out.println("list = " + list);
        Long totalSum = 0L;
        for (Long i : list) {
            totalSum += i;
        }
        if (totalSum == 0) {
            return null;
        }
        for (Long i:list) {
            int percent = Math.round((float) i /totalSum * 100);
            result.add(percent);
        }
        return result;
    }

    /**
     * 소비 데이터 조회
     * @param studentNum
     * @return
     */
    private List<MenuValue> getExpendDetail(Long studentNum) {
        Long bankStudentNum = getBankStudentNum(studentNum);
        QTransactionHistory history = QTransactionHistory.transactionHistory;
        List<Tuple> tupleList = queryFactory.select(history.category, history.amount.sum())
                .from(history)
                .where(history.bankNationStudent.bankNationStudentNum.eq(bankStudentNum)
                        .and(history.type.eq(1))
                        .and(history.category.ne("이체")))
                .groupBy(history.category)
                .orderBy(history.amount.sum().desc())
                .fetch();
        List<MenuValue> list = new ArrayList<>();

        for (Tuple tuple : tupleList) {
            String menu = tuple.get(history.category);
            Long value = tuple.get(history.amount.sum());
            MenuValue menuValue = new MenuValue(menu, value);
            list.add(menuValue);
        }
        return list;
    }

    /**
     * 수입 통계 데이터
     * @param studentNum
     * @return
     */
    private List<MenuValue> getIncomeDetail(Long studentNum) {
        Long bankStudentNum = getBankStudentNum(studentNum);
        QTransactionHistory history = QTransactionHistory.transactionHistory;
        List<Tuple> tupleList = queryFactory.select(history.category, history.amount.sum())
                .from(history)
                .where(history.bankNationStudent.bankNationStudentNum.eq(bankStudentNum)
                        .and(history.type.eq(0)).and(history.category.ne(BankStateType.CATEGORY_JOIN)))
                .groupBy(history.category)
                .orderBy(history.amount.sum().desc())
                .fetch();
        List<MenuValue> list = new ArrayList<>();

        for (Tuple tuple : tupleList) {
            String menu = tuple.get(history.category);
            Long value = tuple.get(history.amount.sum());
            MenuValue menuValue = new MenuValue(menu, value);
            list.add(menuValue);
        }
        return list;
    }

    /**
     * 수입 총 합
     * @param studentNum
     * @return
     */
    private Long getTotalIncome(Long studentNum) {
        Long bankStudentNum = getBankStudentNum(studentNum);
        QTransactionHistory history = QTransactionHistory.transactionHistory;
        return queryFactory.select(history.amount.sum())
                .from(history)
                .where(history.bankNationStudent.bankNationStudentNum.eq(bankStudentNum)
                        .and(history.type.eq(0)).and(history.category.ne(BankStateType.CATEGORY_JOIN)))
                .fetchOne();
    }

    /**
     * 지출 총 합
     * @param studentNum
     * @return
     */
    private Long getTotalexpend(Long studentNum) {
        Long bankStudentNum = getBankStudentNum(studentNum);
        QTransactionHistory history = QTransactionHistory.transactionHistory;
        return queryFactory.select(history.amount.sum())
                .from(history)
                .where(history.bankNationStudent.bankNationStudentNum.eq(bankStudentNum)
                        .and(history.type.eq(1))
                        .and(history.category.ne("이체")))
                .fetchOne();
    }

    /**
     * 월별 수입 조회
     * @param studentNum
     * @return
     */
    private List<Long> getMonthlyIncome(Long studentNum) {
        Long bankStudentNum = getBankStudentNum(studentNum);
        QTransactionHistory history = QTransactionHistory.transactionHistory;
        return queryFactory.select(history.amount.sum())
                .from(history)
                .where(history.bankNationStudent.bankNationStudentNum.eq(bankStudentNum)
                        .and(history.type.eq(0)).and(history.category.ne(BankStateType.CATEGORY_JOIN)))
                .groupBy(history.transactionDate.month())
                .orderBy(history.transactionDate.month().asc())
                .fetch();
    }

    /**
     * 월별 지출 조회
     * @param studentNum
     * @return
     */
//    private List<Long> getMonthlyExpend(Long studentNum, Long nationNum) {
//        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;
//        Long bankStudentNum = queryFactory.select(bankNationStudent.bankNationStudentNum)
//                .from(bankNationStudent)
//                .where(bankNationStudent.nationStudent.nationStudentNum.eq(studentNum)
//                        .and(bankNationStudent.bank.productType.eq(BankStateType.MAIN_ACCOUNT)))
//                .fetchOne();
//        QTransactionHistory history = QTransactionHistory.transactionHistory;
//    }


    /**
     * bankNationStudentNum 조회
     * @param nationStudentNum
     * @return
     */
    private Long getBankStudentNum(Long nationStudentNum) {
        QBankNationStudent bankNationStudent = QBankNationStudent.bankNationStudent;
        return queryFactory.select(bankNationStudent.bankNationStudentNum)
                .from(bankNationStudent)
                .where(bankNationStudent.nationStudent.nationStudentNum.eq(nationStudentNum)
                        .and(bankNationStudent.bank.productType.eq(BankStateType.MAIN_ACCOUNT)))
                .fetchOne();
    }

    /**
     * 나라별 달 조회
     * @param nationNum
     * @return
     */
    private List<YearMonth> getNationMonth(Long nationNum) {
        Nation nation = nationRepository.findByNationNum(nationNum)
                .orElseThrow(() -> new ApiException(ExceptionEnum.NATION_EXIST_EXCEPTION));
        LocalDateTime start = nation.getStartDate();
        LocalDateTime end = nation.getEndDate();

        List<YearMonth> yearMonthList = new ArrayList<>();
        LocalDateTime current = start;
        while (!current.isAfter(end)) {
            YearMonth yearMonth = YearMonth.from(current);
            yearMonthList.add(yearMonth);
            current = current.plusMonths(1); // 다음 달로 이동
        }
        return yearMonthList;
    }
}
