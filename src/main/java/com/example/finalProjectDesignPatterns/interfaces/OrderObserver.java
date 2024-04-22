package com.example.finalProjectDesignPatterns.interfaces;


import com.example.finalProjectDesignPatterns.Enum.PaymentStatus;
import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.Status;
import com.example.finalProjectDesignPatterns.entity.Order;

import java.util.UUID;

public interface OrderObserver {

    void update(Order order);
    ResponseEntity<Order> updateOrderStatus(UUID orderId, Status newStatus);
    ResponseEntity<Order> updatePaymentStatus(UUID orderId, PaymentStatus newStatus);
}
