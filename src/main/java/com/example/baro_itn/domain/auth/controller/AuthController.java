package com.example.baro_itn.domain.auth.controller;

import com.example.baro_itn.common.payload.ApiResponse;
import com.example.baro_itn.domain.auth.dto.request.LoginRequestDto;
import com.example.baro_itn.domain.auth.dto.request.SignupRequestDto;
import com.example.baro_itn.domain.auth.dto.response.LoginResponseDto;
import com.example.baro_itn.domain.auth.service.AuthService;
import com.example.baro_itn.domain.user.dto.UserResponseDto;
import com.example.baro_itn.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "AuthController", description = "로그인 & 회원가입 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "로그인 API")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = LoginRequestDto.class),
                    examples = @ExampleObject(value = """
                            {
                              "username": "김우진",
                              "password": "password"
                            }
                            """)
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "로그인 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponseDto.class)
            )
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }



    @Operation(
            summary = "회원 가입",
            description = "회원 가입 API",
            security = @SecurityRequirement(name = "bearerAuth")  // JWT 인증 필요
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = SignupRequestDto.class),
                    examples = @ExampleObject(value = """
                {
                  "username": "김우진",
                  "password": "password",
                  "nickname": "망고"
                }
                """)
            )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "회원가입 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDto.class)
            )
    )
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto) {
        User user = authService.signup(signupRequestDto);
        return ResponseEntity.ok(ApiResponse.success(UserResponseDto.from(user)));
    }
}
