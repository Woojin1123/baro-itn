package com.example.baro_itn.common.payload;

import com.example.baro_itn.common.enums.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse<T> extends ResponseDto {
    private final T data;

    public ApiResponse(ApiStatus status, T data){
        super(status);
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, data);
    }
}
