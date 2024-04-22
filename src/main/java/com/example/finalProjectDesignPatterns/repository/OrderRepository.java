package com.example.finalProjectDesignPatterns.repository;


import com.example.finalProjectDesignPatterns.dto.Status;
import com.example.finalProjectDesignPatterns.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByStatus(Status status);
}
