package com.example.finalProjectDesignPatterns.controller;


import com.example.finalProjectDesignPatterns.Enum.PaymentStatus;
import com.example.finalProjectDesignPatterns.dto.*;
import com.example.finalProjectDesignPatterns.entity.Order;
import com.example.finalProjectDesignPatterns.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    public ResponseEntity<Order> newOrder(@RequestBody Order order){
        System.out.println(order);
        return orderService.newOrder(order);
    }

    @RequestMapping(value = "/allOrders",method = RequestMethod.GET)
    public ResponseEntity<HashMap<Status, List<Order>>> getAllOrders(){
        ResponseEntity<HashMap<Status, List<Order>>> response = new ResponseEntity<>();
        HashMap<Status, List<Order>> orders = orderService.getPendingOrders();
        if(orders == null){
            response.setMessage("No orders found, please add the order and check back");
            response.setResponseStatus(ReturnType.SUCCESS);
        }else{
            response.setData(orders);
            response.setResponseStatus(ReturnType.SUCCESS);
        }
        return response;
    }


    @PostMapping("/updateStatus/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable UUID orderId, @RequestBody OrderStatusInput status) {
        System.out.println(status);
        return orderService.updateOrderStatus(orderId, status.getStatus());
    }

    @PostMapping("/updatePaymentStatus/{orderId}")
    public ResponseEntity<Order> updatePaymentStatus(@PathVariable UUID orderId, @RequestBody PaymentStatusInput status) {
        return orderService.updatePaymentStatus(orderId, status.getPaymentStatus());
    }
}
