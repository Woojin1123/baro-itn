package com.example.baro_itn.common.exception;

import com.example.baro_itn.common.enums.ErrorCode;
import com.example.baro_itn.common.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.failure(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}
