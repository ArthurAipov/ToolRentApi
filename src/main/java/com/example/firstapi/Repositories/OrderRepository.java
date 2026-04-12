package com.example.firstapi.Repositories;

import com.example.firstapi.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
