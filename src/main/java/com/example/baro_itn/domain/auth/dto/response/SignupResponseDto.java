package com.example.baro_itn.domain.auth.dto.response;

import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SignupResponseDto {
    private Long userId;
    private String username;
    private String nickname;
    private List<UserRole> role;

    public static SignupResponseDto from(User user) {
        return new SignupResponseDto(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRoles()
        );
    }
}
