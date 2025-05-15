package com.example.baro_itn.domain.user.entity;

import com.example.baro_itn.common.enums.RoleType;
import com.example.baro_itn.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Map<RoleType, UserRole> roles;

    public User(Long id, String username, String password, String nickname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.roles = new HashMap<>();
        roles.put(RoleType.USER, UserRole.USER);
    }
}
