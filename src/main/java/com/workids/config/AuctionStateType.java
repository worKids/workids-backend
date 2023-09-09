package com.workids.config;

/**
 * 경매 state, type
 */
public class AuctionStateType {
    // 경매 진행 상태
    public static final int IN_PROGRESS = 0; // 진행중
    public static final int CLOSE = 1; // 종료
    public static final int DELETE = 2; // 삭제

    // 경매 참여 결과 유형
    public static final int NOT_SUBMITTER = 0; // 미참여자
    public static final int SUCCESSFUL_BIDDER = 1; // 낙찰자
    public static final int NON_SUCCESSFUL_BIDDER = 2; // 미낙찰자
}
