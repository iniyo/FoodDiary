package com.service.fooddiary.infrastructure.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.service.fooddiary.infrastructure.messaging.common.KafkaEvent;
import com.service.fooddiary.infrastructure.utils.annotation.KafkaPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * "KafkaEvent" 하위 클래스 중 @KafkaPayload 달린 것들을 찾아,
 * Jackson 다형성 매핑에 자동 등록하는 예시
 */
@Slf4j
@Configuration
public class KafkaEventAutoRegisterConfig implements BeanDefinitionRegistryPostProcessor {

    private final SimpleModule kafkaEventModule = new SimpleModule("KafkaEventModuleAuto");

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 1. 스캐너 생성
        Set<BeanDefinition> candidateComponents = getBeanDefinitions();
        for (BeanDefinition candidate : candidateComponents) {
            String className = candidate.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(className);

                // 4. KafkaEvent 추상 클래스를 상속하는지 체크
                if (KafkaEvent.class.isAssignableFrom(clazz)) {
                    // @KafkaPayload의 value()를 얻어서 Jackson 식별자로 사용
                    KafkaPayload annotation = clazz.getAnnotation(KafkaPayload.class);
                    String typeName = annotation != null ? annotation.value() : "";
                    if (!StringUtils.hasText(typeName)) {
                        // 비어있다면, 간단히 클래스 이름
                        typeName = clazz.getSimpleName();
                    }

                    // 5. SimpleModule에 NamedType 등록
                    kafkaEventModule.registerSubtypes(new NamedType(clazz, typeName));

                    log.info("[KafkaEventAutoRegister] Found class {} => Registered as '{}'", className, typeName);
                }
            } catch (ClassNotFoundException e) {
                log.error("[KafkaEventAutoRegister] Class not found: {}", className, e);
            }
        }
    }

    private static Set<BeanDefinition> getBeanDefinitions() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        // 2. @KafkaPayload 달린 클래스만 골라내는 필터 추가
        TypeFilter filter = new AnnotationTypeFilter(KafkaPayload.class);
        scanner.addIncludeFilter(filter);

        // (선택) 특정 패키지만 스캔하고 싶다면, 여러 패키지로 나눌 수도 있음
        String basePackage = "com.service.fooddiary.infrastructure.messaging.payloads";

        // 3. 스캐닝
        return scanner.findCandidateComponents(basePackage);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        // 6. ObjectMapper 빈이 이미 등록되어 있으면 가져와서 모듈 추가
        //    등록되어 있지 않다면 우리가 새로 등록
        try {
            ObjectMapper objectMapper = beanFactory.getBean(ObjectMapper.class);
            objectMapper.registerModule(kafkaEventModule);
            log.info("[KafkaEventAutoRegister] Registered KafkaEventModule to existing ObjectMapper bean");
        } catch (Exception e) {
            // 만약 ObjectMapper 빈이 없다면, 우리 스스로 만들어 등록
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(kafkaEventModule);
            beanFactory.registerSingleton("objectMapper", objectMapper);
            log.info("[KafkaEventAutoRegister] Created new ObjectMapper bean and registered KafkaEventModule");
        }
    }
}
