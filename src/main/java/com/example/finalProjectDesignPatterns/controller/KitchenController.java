package com.example.finalProjectDesignPatterns.controller;


import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.entity.Order;
import com.example.finalProjectDesignPatterns.services.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/kitchen")
@RestController
public class KitchenController {

    @Autowired
    private KitchenService kitchenService;

    @RequestMapping(value = "/showPreparingOrders", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> getPendingOrders(){
        List<Order> orders = kitchenService.getPreparingOrders();
        if(orders.size() == 0){
            return new ResponseEntity<>("No orders found, please add the order and check back", null, ReturnType.SUCCESS);
        }
        return new ResponseEntity<>("Orders pending in Kitchen", orders, ReturnType.SUCCESS);
    }

    @RequestMapping(value = "/updateStatus/{orderId}")
    public ResponseEntity<List<Order>>  updateStatus(@PathVariable UUID orderId){
        List<Order> orders= kitchenService.updateStatus(orderId);
        return new ResponseEntity<List<Order>>("Order status updated",orders, ReturnType.SUCCESS);
    }

}
