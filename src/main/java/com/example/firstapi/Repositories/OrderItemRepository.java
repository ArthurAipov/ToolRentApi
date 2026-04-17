package com.example.firstapi.Repositories;

import com.example.firstapi.Models.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findByOrderId(Long orderId, Pageable pageable);

    Page<OrderItem> findByToolId(Long toolId, Pageable pageable);

    Page<OrderItem> findByOrderIdAndToolId(Long orderId, Long toolId, Pageable pageable);

    boolean existsByOrderIdAndToolId(Long orderId, Long toolId);

    @Query("""
            select count(oi) > 0
            from OrderItem oi
            where oi.tool.id = :toolId
              and oi.startDate <= :endDate
              and oi.endDate >= :startDate
            """)
    boolean existsOverlappingByToolId(
            @Param("toolId") Long toolId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
            select count(oi) > 0
            from OrderItem oi
            where oi.tool.id = :toolId
              and oi.id <> :orderItemId
              and oi.startDate <= :endDate
              and oi.endDate >= :startDate
            """)
    boolean existsOverlappingByToolIdExcludingId(
            @Param("toolId") Long toolId,
            @Param("orderItemId") Long orderItemId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
