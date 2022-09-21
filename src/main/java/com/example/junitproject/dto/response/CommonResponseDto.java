package com.example.junitproject.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponseDto<T> {

    private Integer code; // 성공 1, 실패 -1
    private String msg;
    private T body;

    @Builder
    public CommonResponseDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
