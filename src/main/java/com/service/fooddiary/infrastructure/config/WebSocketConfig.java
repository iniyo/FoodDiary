package com.service.fooddiary.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 구독할 prefix, 예: /topic, /queue 등
        config.enableSimpleBroker("/topic", "/queue");
        // 서버에 메시지 전송 시 prefix
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // SockJS 지원
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                //.addInterceptors(new CustomHandshakeInterceptor()) // 인터셉터 추가
                .withSockJS(); // socketJs 허용

        // 순수 WebSocket 지원
        registry.addEndpoint("/ws-plain")
                .setAllowedOrigins("*");
                //.addInterceptors(new CustomHandshakeInterceptor()); // 인터셉터 추가
    }
//
//    public static class CustomHandshakeInterceptor implements HandshakeInterceptor {
//        private static final Logger logger = LoggerFactory.getLogger(CustomHandshakeInterceptor.class);
//
//        @Override
//        public boolean beforeHandshake(
//                org.springframework.http.server.ServerHttpRequest request,
//                org.springframework.http.server.ServerHttpResponse response,
//                WebSocketHandler wsHandler,
//                Map<String, Object> attributes) {
//
//            // 요청 헤더 출력
//            HttpHeaders headers = request.getHeaders();
//            logger.info("Incoming WebSocket Request Headers: {}", headers);
//
//            // Sec-WebSocket-Key 확인
//            if (!headers.containsKey("Sec-WebSocket-Key")) {
//                logger.warn("Invalid WebSocket handshake request: missing Sec-WebSocket-Key");
//                return false; // 연결 거부
//            }
//
//            logger.info("WebSocket Handshake Allowed");
//            return true; // 연결 허용
//        }
//
//        @Override
//        public void afterHandshake(
//                org.springframework.http.server.ServerHttpRequest request,
//                org.springframework.http.server.ServerHttpResponse response,
//                WebSocketHandler wsHandler,
//                Exception exception) {
//            logger.info("WebSocket Handshake Completed");
//        }
//    }
}

