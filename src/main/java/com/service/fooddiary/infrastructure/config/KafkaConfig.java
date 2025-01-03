package com.service.fooddiary.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.fooddiary.infrastructure.messaging.common.KafkaEvent;
import com.service.fooddiary.infrastructure.messaging.common.topics.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.service.fooddiary.infrastructure.common.constants.AppConstants.KAFKA_SERVER_HOST_PORT;
import static com.service.fooddiary.infrastructure.common.constants.AppConstants.STRING_ASTERISK;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_HOST_PORT);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, STRING_ASTERISK);

        DefaultKafkaConsumerFactory<String, Object> factory = new DefaultKafkaConsumerFactory<>(props);
        factory.setValueDeserializer(new JsonDeserializer<>(Object.class, objectMapper));
        return factory;
    }

    private Map<String, Object> getProducerConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER_HOST_PORT);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return configProps;
    }

    private <T> ProducerFactory<String, T> createProducerFactory(Class<T> valueType, ObjectMapper objectMapper) {
        Map<String, Object> configProps = getProducerConfig();
        if (valueType == String.class) {
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        } else {
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<>(objectMapper));
        }
    }

    @Bean
    public <T> KafkaTemplate<String, T> createKafkaTemplate(ProducerFactory<String, T> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return createKafkaTemplate(createProducerFactory(String.class, null));
    }

    @Bean
    public KafkaTemplate<String, KafkaEvent> eventKafkaTemplate(ObjectMapper objectMapper) {
        return createKafkaTemplate(createProducerFactory(KafkaEvent.class, objectMapper));
    }

    // kafka consumer는 threadSafe하지 않기 때문에 메시지 처리 순서를 완전하게 보장하기 위해선 토픽의 파티션을 1로 해야 함.
    // 하지만 이렇게 하게되는 경우에는 처리속도가 떨어지기 때문에 파티션을 늘려야하는 경우도 생길 수 있는데
    // kafka는 파티션:컨슈머가 N:1만 허용 됨
    @Bean
    public NewTopic notificationTopic() {
        return new NewTopic(KafkaTopics.NOTIFICATION_TOPIC, 1, (short) 1);
    }
}
