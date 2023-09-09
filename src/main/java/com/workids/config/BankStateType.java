package com.workids.config;

/**
 * 은행(상품, 거래내역) state, type
 */
public class BankStateType {
    // 은행 상품 유형
    public static final int MAIN_ACCOUNT = 0; // 주거래 통장
    public static final int DEPOSIT_ACCOUNT = 1; // 예금 통장

    // 은행 상품 항목 상태
    public static final int IN_USE = 0; // 사용중(개설 가능)
    public static final int UN_USE = 1; // 미사용중(개설 불가=삭제)

    // 은행 상품 가입 상태
    public static final int MAINTAIN = 0; // 가입중
    public static final int EXPIRE = 1; // 만기
    public static final int MID_CANCEL = 2; // 중도 해지

    // 거래 유형
    public static final int DEPOSIT = 0; // 입금
    public static final int WITHDRAW = 1; // 출금
}
