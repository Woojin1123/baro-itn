package com.example.baro_itn.domain.user.entity;

import com.example.baro_itn.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private List<UserRole> roles;

    public User(String username, String password, String nickname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.roles = new ArrayList<>();
        this.roles.add(UserRole.USER);
    }
}
