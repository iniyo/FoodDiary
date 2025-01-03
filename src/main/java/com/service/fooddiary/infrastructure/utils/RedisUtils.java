package com.service.fooddiary.infrastructure.utils;

import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    // redis cluster와 통신하기 위해 사용하는 인터페이스
    private final StatefulRedisClusterConnection<String, String> connection;

    /**
     * 데이터 저장 (동기)
     */
    public void setData(String key, String value) {
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.set(key, value);
    }

    /**
     * 데이터 조회 (동기)
     */
    public String getData(String key) {
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        return commands.get(key);
    }

    /**
     * 데이터 삭제 (동기)
     */
    public void deleteData(String key) {
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.del(key);
    }

    /**
     * 데이터 저장 (비동기)
     */
    public void setDataAsync(String key, String value) {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = connection.async();
        asyncCommands.set(key, value).thenAccept(result -> {
            if ("OK".equals(result)) {
                System.out.println("Data saved successfully");
            } else {
                System.out.println("Failed to save data");
            }
        });
    }

    /**
     * 데이터 조회 (비동기)
     */
    public void getDataAsync(String key) {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = connection.async();
        asyncCommands.get(key).thenAccept(value -> {
            if (value != null) {
                System.out.println("Retrieved data: " + value);
            } else {
                System.out.println("Key not found");
            }
        });
    }

    /**
     * 데이터 삭제 (비동기)
     */
    public void deleteDataAsync(String key) {
        RedisAdvancedClusterAsyncCommands<String, String> asyncCommands = connection.async();
        asyncCommands.del(key).thenAccept(count -> {
            System.out.println("Number of keys deleted: " + count);
        });
    }

    /**
     * 데이터 저장 (Reactive)
     */
    public Mono<String> setDataReactive(String key, String value) {
        RedisAdvancedClusterReactiveCommands<String, String> reactiveCommands = connection.reactive();
        return reactiveCommands.set(key, value);
    }

    /**
     * 데이터 조회 (Reactive)
     */
    public Mono<String> getDataReactive(String key) {
        RedisAdvancedClusterReactiveCommands<String, String> reactiveCommands = connection.reactive();
        return reactiveCommands.get(key);
    }

    /**
     * 데이터 삭제 (Reactive)
     */
    public Mono<Long> deleteDataReactive(String key) {
        RedisAdvancedClusterReactiveCommands<String, String> reactiveCommands = connection.reactive();
        return reactiveCommands.del(key);
    }
}