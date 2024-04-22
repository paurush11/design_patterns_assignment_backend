package com.example.finalProjectDesignPatterns.services;


import com.example.finalProjectDesignPatterns.dto.Status;
import com.example.finalProjectDesignPatterns.entity.Order;
import com.example.finalProjectDesignPatterns.interfaces.OrderObserver;
import com.example.finalProjectDesignPatterns.logger.Logger;
import com.example.finalProjectDesignPatterns.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


public class KitchenService {

    @Autowired
    private OrderRepository orderRepository ;

    @Autowired
    private List<OrderObserver> observers;


    private List<Order> preparingOrders;


    private KitchenService() {
    }


    private static KitchenService instance = null;

    private static Logger logger;

    public static KitchenService getInstance() {
        synchronized(KitchenService.class) {
            if (instance == null) {
                instance = new KitchenService();
                logger = Logger.getInstance();
            }
        }
        return instance;
    }

    public synchronized void loadPreparingOrders(){
        if(preparingOrders == null || preparingOrders.isEmpty()){
            preparingOrders = orderRepository.findByStatus(Status.PREPARING);

        }
    }


    public List<Order> getPreparingOrders() {
        loadPreparingOrders();
        return preparingOrders;
    }

    public List<Order> updateStatus(UUID orderId){
        Optional<Order> order = this.preparingOrders.stream().filter(o-> Objects.equals(o.getId(), orderId)).findFirst();
        if(order.isPresent()){
            Order foundOrder = order.get();
            foundOrder.setStatus(Status.READY);
            notifyOrderService(foundOrder);
            this.preparingOrders.removeIf(o-> Objects.equals(o.getId(), orderId));
        }
        return preparingOrders;
    }


    private void notifyOrderService(Order order){
        this.observers.forEach(observer -> observer.update(order));
    }


}
