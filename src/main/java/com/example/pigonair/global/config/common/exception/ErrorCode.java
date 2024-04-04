package com.example.pigonair.global.config.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    DUPLICATED_EMAIL("DUPLICATED_EMAIL", "중복된 이메일입니다.", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("VALIDATION_ERROR", "잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_OR_PASSWORD("INVALID_EMAIL_OR_PASSWORD", "이메일 혹은 비밀번호가 틀렸습니다. 이메일 혹은 비밀번호를 확인하세요.",
            HttpStatus.BAD_REQUEST),
    FORBIDDEN("FORBIDDEN", "접근 권한이 없습니다. ADMIN에게 문의하세요.", HttpStatus.FORBIDDEN),
    UNAUTHORIZED("UNAUTHORIZED", "로그인 후 이용할 수 있습니다. 계정이 없다면 회원 가입을 진행해주세요.", HttpStatus.UNAUTHORIZED),

    RESERVATION_NOT_FOUND("RESERVATION_NOT_FOUND", "해당 예약을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    PAYMENT_AMOUNT_MISMATCH("PAYMENT_AMOUNT_MISMATCH", "결제 금액과 선택한 좌석의 가격이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    ALREADY_PAID_RESERVATION("ALREADY_PAID_RESERVATION", "이미 결제된 예약입니다.", HttpStatus.BAD_REQUEST),
    UNAVAILABLE_SEAT("UNAVAILABLE_SEAT", "이용 불가능한 좌석입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SEAT("NOT_FOUND_SEAT", "좌석을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_MEMBER("NOT_FOUND_MEMBER", "회원을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    NOT_FOUND_FLIGHT("NOT_FOUND_FLIGHT", "비행기를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    ALREADY_RESERVED_SEAT("ALREADY_RESERVED_SEAT", "이미 예약된 좌석입니다", HttpStatus.CONFLICT),

    INVALID_SEARCH_CONDITION("INVALID_SEARCH_CONDITION", "불가능한 검색 조건입니다.", HttpStatus.BAD_REQUEST),
	INVALID_PATH_VARIABLE("INVALID_PATH_VARIABLE", "출발지 또는 도착지가 잘못되었습니다.", HttpStatus.BAD_REQUEST),
	DATABASE_ERROR("DATABASE_ERROR", "데이터베이스 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String key;
    private final String message;
    private final HttpStatus httpStatus;


    ErrorCode(String key, String message, HttpStatus httpStatus) {
        this.key = key;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

