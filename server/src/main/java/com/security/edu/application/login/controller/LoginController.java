package com.security.edu.application.login.controller;

import com.security.edu.application.login.dto.LoginRequest;
import com.security.edu.application.login.dto.LoginResponse;
import com.security.edu.application.login.service.LoginService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}