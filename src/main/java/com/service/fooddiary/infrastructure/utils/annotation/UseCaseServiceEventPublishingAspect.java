package com.service.fooddiary.infrastructure.utils.annotation;


import com.service.fooddiary.domain.common.DomainEvent;
import com.service.fooddiary.domain.common.DomainEventsHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
class UseCaseServiceEventPublishingAspect {
    private final ApplicationEventPublisher applicationEventPublisher;

    public UseCaseServiceEventPublishingAspect(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Around("@within(fooddiary.infrastructure.utils.annotation.UseCaseService)")
    public Object publishEventsAroundUseCase(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            // UseCaseService 메서드가 정상적으로 실행되면 DomainEventsHolder에서 이벤트 발행
            for (DomainEvent event : DomainEventsHolder.getEvents()) {
                applicationEventPublisher.publishEvent(event);
                System.out.println("Event Published: " + event.getClass().getSimpleName());
            }
            return result;
        } finally {
            // 이벤트 발행 후 항상 DomainEventsHolder 초기화
            DomainEventsHolder.clear();
        }
    }
}
