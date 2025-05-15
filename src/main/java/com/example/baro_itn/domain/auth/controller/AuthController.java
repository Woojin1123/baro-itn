package com.example.baro_itn.domain.auth.controller;

import com.example.baro_itn.common.payload.ApiResponse;
import com.example.baro_itn.domain.auth.dto.request.LoginRequestDto;
import com.example.baro_itn.domain.auth.dto.request.SignupRequestDto;
import com.example.baro_itn.domain.auth.dto.response.LoginResponseDto;
import com.example.baro_itn.domain.auth.dto.response.SignupResponseDto;
import com.example.baro_itn.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto response = authService.signup(signupRequestDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
