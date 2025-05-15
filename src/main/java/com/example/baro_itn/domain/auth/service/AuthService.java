package com.example.baro_itn.domain.auth.service;

import com.example.baro_itn.common.enums.ErrorCode;
import com.example.baro_itn.common.exception.ApiException;
import com.example.baro_itn.common.utils.JwtUtil;
import com.example.baro_itn.domain.auth.dto.request.LoginRequestDto;
import com.example.baro_itn.domain.auth.dto.request.SignupRequestDto;
import com.example.baro_itn.domain.auth.dto.response.LoginResponseDto;
import com.example.baro_itn.domain.user.dto.UserResponseDto;
import com.example.baro_itn.domain.user.entity.User;
import com.example.baro_itn.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername());

        if (user == null || !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.createToken(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                user.getNickname());

        return new LoginResponseDto(token);
    }

    public UserResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        if (userRepository.isExists(username)) {
            throw new ApiException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User saveUser = userRepository.save(
                signupRequestDto.getUsername(),
                passwordEncoder.encode(signupRequestDto.getPassword()),
                signupRequestDto.getNickname());

        return UserResponseDto.from(saveUser);
    }
}
