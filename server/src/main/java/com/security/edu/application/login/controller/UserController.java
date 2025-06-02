package com.security.edu.application.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/me")
    public Map<String, String> getCurrentUser(HttpServletRequest request) {
        // JwtInterceptor가 정상 통과되었으므로, request attribute에 username이 있음
        String username = (String) request.getAttribute("username");
        Map<String, String> result = new HashMap<>();
        result.put("username", username);
        return result;
    }
}
