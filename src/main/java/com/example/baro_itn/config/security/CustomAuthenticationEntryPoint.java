package com.example.baro_itn.config.security;

import com.example.baro_itn.common.enums.ErrorCode;
import com.example.baro_itn.common.exception.ApiException;
import com.example.baro_itn.common.payload.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws ServletException, IOException {
        ApiException e = new ApiException(ErrorCode.INVALID_TOKEN);
        response.setStatus(e.getErrorCode().getHttpStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = ErrorResponse.failure(e.getErrorCode());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
