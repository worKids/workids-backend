package com.workids.config;

/**
 * 법 state, type
 */
public class LawStateType {
    // 법 항목 유형
    public static final int FINE = 0; // 벌금
    public static final int PENALTY = 1; // 벌칙

    // 법 항목 상태
    public static final int IN_USE = 0; // 사용중
    public static final int UN_USE = 1; // 미사용중(수정 전 항목 or 삭제)

    // 벌칙 수행 상태
    public static final int PENALTY_UN_COMPLETE = 0; // 벌칙 미수행
    public static final int PENALTY_COMPLETE = 1; // 벌칙 수행
}
