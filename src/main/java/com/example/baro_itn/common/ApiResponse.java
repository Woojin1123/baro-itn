package com.example.baro_itn.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.ErrorResponse;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private ApiStatus status;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, data);
    }
}
