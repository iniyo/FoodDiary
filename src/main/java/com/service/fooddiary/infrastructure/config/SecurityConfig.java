package com.service.fooddiary.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.config.Customizer;

@Configuration
class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정, 필요에 따라 disable 가능
                .csrf(AbstractHttpConfigurer::disable)

                // 인증이 필요한 URL은 여기서 설정
                .authorizeHttpRequests(auth -> auth
                        // /sign/** 에는 인증 없이 접근 가능하다고 가정
                        .requestMatchers("/sign/**").permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 Client 사용
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}


//@Configuration
//public class SecurityConfig {
//
//    private final OAuth2SuccessHandler oAuth2SuccessHandler;
//    private final CustomOAuth2UserServiceAdapter customOAuth2UserServiceAdapter;
//
//    public SecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler,
//                          CustomOAuth2UserServiceAdapter customOAuth2UserServiceAdapter) {
//        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
//        this.customOAuth2UserServiceAdapter = customOAuth2UserServiceAdapter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll() // Swagger 관련 URL 허용
//                        .anyRequest().authenticated()
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserServiceAdapter))
//                        .successHandler(oAuth2SuccessHandler)
//                );
//        return http.build();
//    }
//
//    @Bean(name = "kakaoJwtDecoder")
//    public JwtDecoder kakaoJwtDecoder() {
//        return JwtDecoders.fromIssuerLocation("https://kauth.kakao.com");
//    }
//
////    @Bean(name = "googleJwtDecoder")
////    public JwtDecoder googleJwtDecoder() {
////        return JwtDecoders.fromIssuerLocation("https://accounts.google.com");
////    }
////
////    @Bean(name = "naverJwtDecoder")
////    public JwtDecoder naverJwtDecoder() {
////        return JwtDecoders.fromIssuerLocation("https://nid.naver.com");
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
