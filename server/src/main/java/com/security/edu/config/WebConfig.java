package com.security.edu.config;

import com.security.edu.security.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization");
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 아래 경로는 JWT가 있어야만 접근 허용하도록 설정 (로그인 제외)
                .addPathPatterns("/api/memo/**", "/api/me")
                .excludePathPatterns("/api/login");
        // 필요에 따라 /api/memo/deleteOne, /api/memo/delete 등도 포함
    }
}