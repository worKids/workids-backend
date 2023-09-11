package com.workids.global.config.stateType;

/**
 * 소비 state, type
 */
public class ConsumptionStateType {
    // 소비 항목 상태
    public static final int IN_USE = 0; // 사용중
    public static final int UN_USE = 1; // 미사용중(수정 전 항목 or 삭제)

    // 소비 신청 상태
    public static final int BEFORE_CHECK = 0; // 대기
    public static final int APPROVAL = 1; // 승인
    public static final int REFUSE = 2; // 거절
    public static final int CANCEL = 3; // 취소
}
