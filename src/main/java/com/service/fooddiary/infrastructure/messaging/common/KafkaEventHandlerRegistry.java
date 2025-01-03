package com.service.fooddiary.infrastructure.messaging.common;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 리플렉션으로 KafkaEventHandler<T>의 T를 추론해
 * Handler를 자동 등록
 */
@Component
@RequiredArgsConstructor
public class KafkaEventHandlerRegistry {

    private final ApplicationContext applicationContext;

    // "이벤트 실제 클래스" -> "핸들러 Bean"
    private final Map<Class<? extends KafkaEvent>, KafkaEventHandler<? extends KafkaEvent>> handlerMap = new HashMap<>();

    @PostConstruct
    private void init() {
        // 스프링 컨텍스트에서 모든 KafkaEventHandler Bean을 검색
        var beans = applicationContext.getBeansOfType(KafkaEventHandler.class);

        for (KafkaEventHandler<?> handlerBean : beans.values()) {
            // handlerBean이 처리하는 이벤트 클래스를 얻는다
            Class<? extends KafkaEvent> eventClass = handlerBean.getEventType();

            // 매핑
            handlerMap.put(eventClass, handlerBean);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends KafkaEvent> KafkaEventHandler<T> getHandler(Class<T> eventClass) {
        return (KafkaEventHandler<T>) handlerMap.get(eventClass);
    }
}