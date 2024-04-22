package com.example.finalProjectDesignPatterns.repository;


import com.example.finalProjectDesignPatterns.entity.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish,Long> {
}
