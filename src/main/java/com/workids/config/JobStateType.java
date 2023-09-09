package com.workids.config;

/**
 * 직업 state, type
 */
public class JobStateType {
    // 직업 항목 상태
    public static final int IN_USE = 0; // 사용중
    public static final int UN_USE = 1; // 미사용중(수정 전 항목 or 삭제)

    // 재직 상태
    public static final int EMPLOY = 0; // 재직중
    public static final int UN_EMPLOY = 1; // 미재직중
}
