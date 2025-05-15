package com.example.baro_itn.common.payload;

import com.example.baro_itn.common.enums.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto {
    private final ApiStatus status;
}
