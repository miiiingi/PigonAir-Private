package com.example.pigonair.global.config.common.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<D> {
    private final String resultCode;
    private final String message;
    private final D data;

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>("SUCCESS", message, data);
    }

    public static <T> ResponseDto<T> error(String key, String message, T data) {
        return new ResponseDto<>(key, message, data);
    }

}