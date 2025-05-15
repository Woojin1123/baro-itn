package com.example.baro_itn.common.payload;

import com.example.baro_itn.common.enums.ApiStatus;
import com.example.baro_itn.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse extends ResponseDto {
    private final String code;
    private final String message;

    public ErrorResponse(ApiStatus status, String code, String message) {
        super(status);
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse failure(ErrorCode errorCode) {
        return new ErrorResponse(ApiStatus.FAILURE, errorCode.getCode(), errorCode.getMessage());
    }
}
