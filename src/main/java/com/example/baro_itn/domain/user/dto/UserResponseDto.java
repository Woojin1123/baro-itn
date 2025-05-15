package com.example.baro_itn.domain.user.dto;

import com.example.baro_itn.common.enums.RoleType;
import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String username;
    private String nickname;
    private Map<RoleType,UserRole> roles;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRoles()
        );
    }
}
