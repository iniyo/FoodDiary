package com.service.fooddiary.infrastructure.persistence.repository;

import com.service.fooddiary.infrastructure.persistence.entity.outbox.OutboxEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface JpaOutBoxRepository extends JpaRepository<OutboxEntity, Long> {

    /**
     * 아직 발행되지 않았고(deadLetter=false),
     * published=false 인 레코드들을 PESSIMISTIC_WRITE 락 모드로 가져온다.
     * (SELECT ... FOR UPDATE)
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT o
        FROM OutboxEntity o
        WHERE o.published = false
          AND o.deadLetter = false
        ORDER BY o.id ASC
        """)
    List<OutboxEntity> findUnpublishedForUpdate(Pageable pageable);

    /**
     * 오래된(published=true) Outbox 레코드 정리용
     */
    @Modifying
    @Query("""
        DELETE FROM OutboxEntity o
        WHERE o.published = true
          AND o.updatedAt < :threshold
        """)
    int deleteOldPublishedRecords(@Param("threshold") LocalDateTime threshold);

    /**
     * deadLetter 된 레코드 중 오래된 것 정리, 필요 시 추가
     */
    @Modifying
    @Query("""
        DELETE FROM OutboxEntity o
        WHERE o.deadLetter = true
          AND o.updatedAt < :threshold
        """)
    int deleteOldDeadLetterRecords(@Param("threshold") LocalDateTime threshold);

    // 기존에 사용하던 메서드가 있으면 그대로 병행 사용 가능
    // e.g. findAllByPublishedFalse();
}