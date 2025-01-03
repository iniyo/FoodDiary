package com.service.fooddiary.infrastructure.utils.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

//@Aspect
//@Component
//public class UseCaseTransactional {
//
//    private final PlatformTransactionManager transactionManager;
//
//    public UseCaseTransactional(PlatformTransactionManager transactionManager) {
//        this.transactionManager = transactionManager;
//    }
//
//    @Around("@within(useCase)")
//    public Object handleTransaction(ProceedingJoinPoint joinPoint, UseCase useCase) throws Throwable {
//        // 1. useCase.transactional()이 false이면 트랜잭션 없이 proceed()
//        if (!useCase.transactional()) {
//            return joinPoint.proceed();
//        }
//
//        // 2. 트랜잭션 정의 생성
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setName(joinPoint.getSignature().toShortString());
//        def.setPropagationBehavior(useCase.propagation().value());
//        def.setIsolationLevel(useCase.isolation().value());
//        def.setReadOnly(useCase.readOnly());
//
//        // 3. 트랜잭션 시작
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            // 4. 핵심 로직 실행
//            Object result = joinPoint.proceed();
//            // 5. 성공 시 커밋
//            transactionManager.commit(status);
//            return result;
//        } catch (Throwable ex) {
//            // 6. 예외 시 롤백
//            transactionManager.rollback(status);
//            throw ex;
//        }
//    }
//}
