package com.example.firstapi.Repositories;

import com.example.firstapi.Models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByApproved(Boolean approved, Pageable pageable);

    Page<Order> findByUserIdAndApproved(Long userId, Boolean approved, Pageable pageable);
}