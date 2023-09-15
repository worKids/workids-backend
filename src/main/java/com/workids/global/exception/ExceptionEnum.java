package com.workids.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "내부 문제로 다음번에 다시 시도해주세요."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0002", "내부 문제로 다음번에 다시 시도해주세요."),
    API_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "E0003", "존재하지 않는 API 입니다."),
    API_METHOD_NOT_ALLOWED_EXCEPTION(HttpStatus.METHOD_NOT_ALLOWED, "E0004", "지원하지 않는 Method 입니다."),
    API_PARAMETER_EXCEPTION(HttpStatus.BAD_REQUEST, "E0005", "파라미터 타입과 값을 확인하세요."),
    MEMBER_ACCESS_EXCEPTION(HttpStatus.FORBIDDEN, "M0001", "접근 권한이 없습니다."),
    TEACHER_NOT_MATCH_EXCEPTION(HttpStatus.CREATED, "T0001", "선생님이 일치하지 않습니다.."),
    STUDENT_NOT_MATCH_EXCEPTION(HttpStatus.CREATED, "S0001", "학생과 일치하지 않습니다."),

    // 나라
    NATION_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "N0001", "나라가 존재하지 않습니다."),
    NATIONSTUDENT_JOIN_EXCEPTION(HttpStatus.FORBIDDEN, "N002", "나라에 가입할 수 없습니다."),
    NATION_CODE_NOT_MATCH_EXCEPTION(HttpStatus.FORBIDDEN, "N003", "나라코드가 일치하지 않습니다."),
    NATION_NOT_JOIN_EXCEPTION(HttpStatus.FORBIDDEN, "N004", "가입 권한이 없습니다."),
    CITIZEN_NOT_JOIN_EXCEPTION(HttpStatus.FORBIDDEN, "N005", "학생의 학급번호 중복입니다."),
    NATION_NOT_DELETE_EXCEPTION(HttpStatus.FORBIDDEN, "N006", "나라에 가입된 학생이 있어 나라를 삭제할 수 없습니다."),
    NATION_EXIST_EXCEPTION(HttpStatus.CONFLICT, "N0007","중복된 나라 이름입니다"),

    SERVER_NOT_CONNECT_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "S0001", "서비스가 연결되지 않았습니다."),

    // 나라-학생
    NATION_STUDENT_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "NS001", "나라와 연결된 해당 학생이 존재하지 않습니다."),

    // ===== 은행 =====
    BANK_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "B0001", "해당 은행 상품이 존재하지 않습니다."),
    // 예금 가입 불가
    BANK_NOT_VALID_PRODUCT_CREATE_EXCEPTION(HttpStatus.CONFLICT, "B0002", "만기일이 나라 종료일 이후로 해당 은행 상품을 가입할 수 없습니다."),
    BANK_NOT_ENOUGH_AMOUNT_CREATE_EXCEPTION(HttpStatus.CONFLICT, "B0003", "주거래 통장의 잔액이 충분하지 않아, 해당 은행 상품에 예치할 수 없습니다."),
    BANK_NOT_VALID_AMOUNT_CREATE_EXCEPTION(HttpStatus.CONFLICT, "B0004", "유효한 예금 금액을 입력하지 않아, 해당 은행 상품에 예치할 수 없습니다."),

    //BANKNATIONSTUDENT_NOT_CREATE_EXCEPTION(HttpStatus.CONFLICT, "B0003", "계좌번호가 이미 존재합니다.(난수 발생 실패)"),

    // ===== 법 =====
    LAW_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "L0001", "해당 법이 존재하지 않습니다."),
    LAW_NATION_STUDENT_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "L0002", "법 부여 내역이 존재하지 않습니다."),
    LAW_NOT_ENOUGH_AMOUNT_EXCEPTION(HttpStatus.CONFLICT, "L0003", "주거래 통장의 잔액이 충분하지 않아, 벌금을 부여할 수 없습니다."),

    // ===== 소비 =====
    CONSUMPTION_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "C0001", "해당 소비 항목이 존재하지 않습니다."),
    CONSUMPTION_NATION_STUDENT_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "C0002", "소비 신청 내역이 존재하지 않습니다."),
    CONSUMPTION_NOT_ENOUGH_AMOUNT_EXCEPTION(HttpStatus.CONFLICT, "C0003", "주거래 통장의 잔액이 충분하지 않아, 소비 내역을 승인할 수 없습니다."),

    //부동산
    AUCTION_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "A0001", "해당 경매 항목이 존재하지 않습니다."),
    AUCTION_SEAT_NOT_EXIST_EXCEPTION(HttpStatus.NOT_FOUND, "A0002", "경매 자리가 존재하지 않습니다."),
    AUCTION_NOT_CONTINUE_EXCEPTION(HttpStatus.NOT_FOUND, "A0003", "진행 중인 경매가 아닙니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}