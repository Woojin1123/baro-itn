package com.example.baro_itn.common.exception;

import com.example.baro_itn.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
