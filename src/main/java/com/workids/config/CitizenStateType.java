package com.workids.config;

/**
 * 국민 목록 state, type
 */
public class CitizenStateType {
    // 국민 목록 참여 상태
    public static final int BEFORE_JOIN = 0; // 등록중(가입 전)
    public static final int IN_NATION = 1; // 참여중(가입 완료)
    public static final int LEAVE_NATION = 2; // 미참여중(추방 or 탈퇴)
}
