package com.workids.global.config.stateType;

/**
 * 나라 state, type
 */
public class NationStateType {
    // 나라 운영 상태
    public static final int BEFORE_OPERATE = 0; // 운영 대기
    public static final int IN_OPERATE = 1; // 운영중
    public static final int END_OPERATE = 2; // 운영 종료

    // 학생 나라 참여 상태
    public static final int IN_NATION = 0; // 참여중(가입 완료)
    public static final int LEAVE_NATION = 1; // 미참여중(추방 or 탈퇴)
}
