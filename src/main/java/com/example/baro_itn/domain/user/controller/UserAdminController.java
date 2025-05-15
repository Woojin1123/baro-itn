package com.example.baro_itn.domain.user.controller;

import com.example.baro_itn.common.payload.ApiResponse;
import com.example.baro_itn.domain.user.dto.UserResponseDto;
import com.example.baro_itn.domain.user.service.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/users")
@Tag(name = "UserAdminController", description = "어드민 유저 API")
public class UserAdminController {
    private final UserAdminService userAdminService;

    @Operation(
            summary = "유저 권한을 관리자(Admin)로 변경",
            description = "특정 유저의 권한을 ADMIN으로 변경하는 API 로그인 후 발행되는 Bearer Token 필요",
            security = @SecurityRequirement(name = "BearerAuth"))
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "권한 변경 성공",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class),  // ApiResponse 래퍼 클래스 명시
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = """
            {
              "status": "SUCCESS",
              "data": { 
                "userId": 1,
                "username": "김우진",
                "nickname": "만두",
                "roles": {
                  "USER": "ADMIN"
                }
              }
            }
            """)
            )
    )
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse<UserResponseDto>> setRoleAdmin(@PathVariable Long userId) {
            UserResponseDto userResponseDto = userAdminService.setRoleAdmin(userId);
        return ResponseEntity.ok(ApiResponse.success(userResponseDto));
    }
}
