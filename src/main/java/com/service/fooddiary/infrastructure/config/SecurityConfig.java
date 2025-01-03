package com.service.fooddiary.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@Profile("dev") // 'dev' 프로파일에서만 활성화
//class SecurityConfig {
//
//    // 로그인 비활성화: 테스트 용도
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        // Swagger 경로에 대해 인증 없이 허용
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//                        .anyRequest().authenticated() // 그 외의 요청은 인증 필요
//                )
//                .csrf(AbstractHttpConfigurer::disable); // CSRF 비활성화 (테스트용)
//        return http.build();
//    }
//}
