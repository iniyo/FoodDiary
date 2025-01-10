package com.service.fooddiary.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
class SchedulingConfig {
    // 필요하다면 특정 스케줄러 Bean 설정 추가 가능
}