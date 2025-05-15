package com.example.baro_itn.domain.auth;

import com.example.baro_itn.common.exception.ApiException;
import com.example.baro_itn.common.utils.JwtUtil;
import com.example.baro_itn.domain.auth.dto.request.LoginRequestDto;
import com.example.baro_itn.domain.auth.dto.request.SignupRequestDto;
import com.example.baro_itn.domain.auth.dto.response.LoginResponseDto;
import com.example.baro_itn.domain.auth.service.AuthService;
import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;

    private SignupRequestDto signupRequestDto;
    private LoginRequestDto loginRequestDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        signupRequestDto = new SignupRequestDto("user1", "123", "nickname");
        loginRequestDto = new LoginRequestDto("user1", "123");
        testUser = new User(1L, "user1", "encodedPwd", "nickname");
    }

    @Nested
    @DisplayName("회원가입")
    class 회원가입 {
        @Test
        void 회원가입_성공() {
            // given
            given(userRepository.isExists("user1")).willReturn(false);
            given(passwordEncoder.encode("123")).willReturn("encodedPwd");
            given(userRepository.save("user1", "encodedPwd", "nickname")).willReturn(testUser);

            // when
            User user = authService.signup(signupRequestDto);

            // then
            assertEquals(user.getUsername(), "user1");
            assertEquals(user.getPassword(), "encodedPwd");
            assertEquals(user.getNickname(), "nickname");
        }
    }

    @Nested
    @DisplayName("로그인")
    class 로그인 {

        @Test
        void 로그인_정상동작() {
            // given
            User savedUser = new User(1L, "user1", "encodedPwd", "nickname");
            given(userRepository.findByUsername("user1")).willReturn(savedUser);
            given(passwordEncoder.matches("123", "encodedPwd")).willReturn(true);
            when(jwtUtil.createToken(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getRoles(),
                    savedUser.getNickname()))
                    .thenReturn("Bearer Token");

            // when
            LoginResponseDto responseDto = authService.login(loginRequestDto);

            // then
            assertNotNull(responseDto);
            assertEquals(responseDto.getToken(), "Bearer Token");
        }

        @Test
        void 가입되지_않은_유저의_경우_에러를_던진다() {
            // given
            given(userRepository.findByUsername("user1")).willReturn(null);

            // when & then
            assertThrows(ApiException.class, () -> {
                authService.login(loginRequestDto);
            });
        }

        @Test
        void 이메일과_비밀번호가_일치하지_않을_경우_에러를_던진다() {
            // given
            User savedUser = new User(1L, "user1", "encodedPwd", "nickname");
            when(userRepository.findByUsername("user1")).thenReturn(savedUser);
            when(passwordEncoder.matches("123", "encodedPwd")).thenReturn(false);

            // when & then
            assertThrows(ApiException.class, () -> {
                authService.login(loginRequestDto);
            });
        }
    }
}
