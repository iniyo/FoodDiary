package com.service.fooddiary.infrastructure.config.redis;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class LettuceConfig {

    private final RedisClusterProperties redisClusterProperties;

    public LettuceConfig(RedisClusterProperties redisClusterProperties) {
        this.redisClusterProperties = redisClusterProperties;
    }

    @Bean
    public List<RedisURI> redisURIs() {
        List<RedisURI> redisURIs = new ArrayList<>();
        for (String node : redisClusterProperties.getNodes()) {
            String[] parts = node.split(":");
            redisURIs.add(RedisURI.create(parts[0], Integer.parseInt(parts[1])));
        }
        return redisURIs;
    }

    @Bean
    public RedisClusterClient redisClusterClient(List<RedisURI> redisURIs) {
        return RedisClusterClient.create(redisURIs);
    }

    @Bean
    public StatefulRedisClusterConnection<String, String> statefulRedisClusterConnection(RedisClusterClient redisClusterClient) {
        return redisClusterClient.connect();
    }
}
