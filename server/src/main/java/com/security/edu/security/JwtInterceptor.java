package com.security.edu.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 1) Authorization 헤더에서 "Bearer {token}" 추출
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authorization header is missing or invalid");
            return false;
        }

        String token = authHeader.substring(7);
        // 2) 토큰 유효성 검증
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired JWT token");
            return false;
        }

        // 3) 토큰에서 username을 꺼내어 request attribute에 저장해 두면, Controller에서 꺼내 쓸 수 있음
        String username = jwtUtil.getUsernameFromToken(token);
        request.setAttribute("username", username);

        return true;
    }
}
