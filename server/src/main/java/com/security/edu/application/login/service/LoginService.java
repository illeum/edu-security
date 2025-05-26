package com.security.edu.application.login.service;

import com.security.edu.application.login.dto.LoginRequest;
import com.security.edu.application.login.dto.LoginResponse;
import com.security.edu.application.login.repository.UserRepository;
import com.security.edu.domain.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user != null && user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("로그인 성공"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("아이디 또는 비밀번호가 틀렸습니다"));
        }
    }
}