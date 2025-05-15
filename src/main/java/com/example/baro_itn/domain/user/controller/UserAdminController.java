package com.example.baro_itn.domain.user.controller;

import com.example.baro_itn.common.payload.ApiResponse;
import com.example.baro_itn.domain.user.dto.UserResponseDto;
import com.example.baro_itn.domain.user.service.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/users")
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PatchMapping("/{userId}/roles")
    public ResponseEntity<ApiResponse<UserResponseDto>> setRoleAdmin(@PathVariable Long userId) {
        UserResponseDto userResponseDto = userAdminService.setRoleAdmin(userId);
        return ResponseEntity.ok(ApiResponse.success(userResponseDto));
    }
}
