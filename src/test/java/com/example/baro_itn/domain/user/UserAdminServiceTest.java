package com.example.baro_itn.domain.user;

import com.example.baro_itn.common.enums.RoleType;
import com.example.baro_itn.common.exception.ApiException;
import com.example.baro_itn.domain.user.dto.UserResponseDto;
import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.enums.UserRole;
import com.example.baro_itn.domain.user.repository.UserRepository;
import com.example.baro_itn.domain.user.service.UserAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserAdminServiceTest {
    @InjectMocks
    private UserAdminService userAdminService;

    @Mock
    private UserRepository userRepository;

    private User admin;
    private User user1;
    private User target;

    @BeforeEach
    void setUp() {
        Map<RoleType, UserRole> adminRoles = new HashMap<>();
        adminRoles.put(RoleType.USER, UserRole.ADMIN);

        Map<RoleType, UserRole> userRoles = new HashMap<>();
        userRoles.put(RoleType.USER, UserRole.USER);

        admin = new User(1L, "admin", "pwd", "admin", adminRoles);
        target = new User(2L, "target", "pwd", "target", userRoles);
        user1 = new User(3L, "user1", "pwd", "user1", userRoles);

    }

    @Test
    void 어드민_권한_변경_성공() {
        //given
        given(userRepository.findByUsername("admin")).willReturn(admin);
        given(userRepository.findByUserId(2L)).willReturn(target);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("admin", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when
        UserResponseDto responseDto = userAdminService.setRoleAdmin(2L);

        //then
        assertEquals(responseDto.getUserId(), 2L);
        assertEquals(responseDto.getUsername(), "target");
        assertEquals(responseDto.getRoles().get(RoleType.USER), UserRole.ADMIN);
    }

    @Test
    void 어드민_권한_변경_실패() {
        //given
        given(userRepository.findByUsername("user1")).willReturn(user1);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("user1", null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //when & then
        assertThrows(ApiException.class, () -> {
            userAdminService.setRoleAdmin(2L);
        });
    }
}
