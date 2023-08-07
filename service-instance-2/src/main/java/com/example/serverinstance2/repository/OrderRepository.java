package com.example.serverinstance2.repository;

import com.example.serverinstance2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
