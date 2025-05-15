package com.example.baro_itn.domain.user.service;

import com.example.baro_itn.common.enums.ErrorCode;
import com.example.baro_itn.common.enums.RoleType;
import com.example.baro_itn.common.exception.ApiException;
import com.example.baro_itn.domain.user.dto.UserResponseDto;
import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.enums.UserRole;
import com.example.baro_itn.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminService {
    private final UserRepository userRepository;

    public UserResponseDto setRoleAdmin(Long userId) {
        User currentUser = getCurrentUser();
        if (!isUserAdmin(currentUser)) {
            throw new ApiException(ErrorCode.UNAUTHORIZED);
        }

        User user = userRepository.findByUserId(userId);
        user.getRoles().put(RoleType.USER, UserRole.ADMIN);
        userRepository.update(user);

        return UserResponseDto.from(user);
    }

    private User getCurrentUser() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", user.getName());
        return userRepository.findByUsername(user.getName());
    }

    private boolean isUserAdmin(User user) {
        UserRole userRole = user.getRoles().get(RoleType.USER);

        return userRole == UserRole.ADMIN;
    }
}
