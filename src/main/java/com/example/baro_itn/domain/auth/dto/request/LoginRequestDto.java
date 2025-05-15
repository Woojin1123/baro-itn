package com.example.baro_itn.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 요청")
public class LoginRequestDto {
    @Schema(description = "사용자 이름", example = "김우진")
    private String username;
    @Schema(description = "비밀번호", example = "password")
    private String password;
}
