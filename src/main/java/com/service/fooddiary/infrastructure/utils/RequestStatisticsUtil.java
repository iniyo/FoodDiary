package com.service.fooddiary.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class RequestStatisticsUtil {

    /**
     * 요청 처리 결과 통계 정보를 담기 위한 DTO 클래스
     */
    public static class RequestStatistics {
        private final int totalRequests;
        private final int successCount;
        private final int failureCount;

        public RequestStatistics(int totalRequests, int successCount, int failureCount) {
            this.totalRequests = totalRequests;
            this.successCount = successCount;
            this.failureCount = failureCount;
        }

        public int getTotalRequests() {
            return totalRequests;
        }

        public int getSuccessCount() {
            return successCount;
        }

        public int getFailureCount() {
            return failureCount;
        }

        @Override
        public String toString() {
            return String.format("Total: %d, Success: %d, Failure: %d",
                    totalRequests, successCount, failureCount);
        }
    }

    /**
     * 주어진 요청 리스트를 비동기 방식으로 처리하며, 모든 요청 완료 후 통계를 반환한다.
     *
     * @param requests       처리할 요청 리스트
     * @param requestHandler 각 요청을 처리하는 함수(성공 시 true, 실패 시 false 반환)
     * @param onComplete     모든 처리 완료 후 후속 작업을 실행하기 위한 콜백 (선택적)
     * @param executor       비동기 작업을 실행할 스레드 풀 (null일 경우 ForkJoinPool.commonPool() 사용)
     * @param <T>            요청의 타입 파라미터
     * @return CompletableFuture<RequestStatistics> 최종 통계 정보를 담고 있는 CompletableFuture
     */
    public static <T> CompletableFuture<RequestStatistics> processRequests(
            List<T> requests,
            Function<T, Boolean> requestHandler,
            Consumer<RequestStatistics> onComplete,
            Executor executor
    ) {
        int totalRequests = (requests == null) ? 0 : requests.size();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        if (requests == null || requests.isEmpty()) {
            // 요청이 없을 경우 즉시 완료된 CompletableFuture 반환
            RequestStatistics emptyResult = new RequestStatistics(0, 0, 0);
            if (onComplete != null) {
                onComplete.accept(emptyResult);
            }
            return CompletableFuture.completedFuture(emptyResult);
        }

        // 지정된 executor가 없으면 ForkJoinPool.commonPool() 사용
        Executor executionPool = (executor != null) ? executor : ForkJoinPool.commonPool();

        // 각 요청을 supplyAsync 로 비동기 처리
        CompletableFuture<?>[] futures = requests.stream()
                .map(request -> CompletableFuture.supplyAsync(() -> {
                    try {
                        boolean isSuccess = requestHandler.apply(request);
                        if (isSuccess) {
                            successCount.incrementAndGet();
                        } else {
                            failureCount.incrementAndGet();
                        }
                        return isSuccess;
                    } catch (Exception e) {
                        log.error("Request error: {}", request, e);
                        failureCount.incrementAndGet();
                        return false; // 요청 실패 처리
                    }
                }, executionPool))
                .toArray(CompletableFuture[]::new);

        // 모든 비동기 요청이 완료된 뒤 통계를 구성하여 반환
        return CompletableFuture.allOf(futures)
                .handle((ignored, throwable) -> {
                    // allOf로 묶인 Future 중 하나라도 예외가 발생하면 throwable에 설정됨
                    if (throwable != null) {
                        log.error("Error during request processing", throwable);
                    }
                    // 최종 통계 객체 생성
                    RequestStatistics stats = new RequestStatistics(
                            totalRequests,
                            successCount.get(),
                            failureCount.get()
                    );
                    // 콜백이 등록되어 있다면 실행
                    if (onComplete != null) {
                        onComplete.accept(stats);
                    }
                    // 통계 객체 반환
                    return stats;
                });
    }
}
