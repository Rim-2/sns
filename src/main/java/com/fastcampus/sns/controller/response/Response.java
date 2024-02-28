package com.fastcampus.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T result;

    // 에러 발생시
    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }

    // 성공시
    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }
}
