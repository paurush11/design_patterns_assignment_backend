package com.example.finalProjectDesignPatterns.services;

import com.example.finalProjectDesignPatterns.Enum.PaymentStatus;
import com.example.finalProjectDesignPatterns.decorators.BaseDishAPI;
import com.example.finalProjectDesignPatterns.decorators.CheeseDecorator;
import com.example.finalProjectDesignPatterns.decorators.ExtraMeatDecorator;
import com.example.finalProjectDesignPatterns.decorators.ToppingDecorator;
import com.example.finalProjectDesignPatterns.dto.DishInput;
import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.dto.Status;
import com.example.finalProjectDesignPatterns.entity.Dish;
import com.example.finalProjectDesignPatterns.entity.Order;
import com.example.finalProjectDesignPatterns.entity.OrderDish;
import com.example.finalProjectDesignPatterns.interfaces.OrderObserver;
import com.example.finalProjectDesignPatterns.logger.Logger;
import com.example.finalProjectDesignPatterns.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements OrderObserver {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private Logger logger;


    public ResponseEntity<Order> newOrder(Order order) {
        try {
            logger.info("\n OrderService: New order received with id :" + order.getId());
            double total = 0;
            for (DishInput d : order.getDishes()) {
                BaseDishAPI cd = menuService.getDish(d.getId());
                if(cd instanceof Dish)
                    ((Dish)cd).setPrice(d.getPrice());
                if (d.isCustomized()) {
                    if (d.getCheesePreference() > 0)
                        cd = new CheeseDecorator(cd, d.getCheesePreference());
                    if (d.getExtraMeat() != null)
                        cd = new ExtraMeatDecorator(cd, d.getExtraMeat());
                    if (!CollectionUtils.isEmpty(d.getToppings()))
                        cd = new ToppingDecorator(cd, d.getToppings());
                }
                OrderDish orderDish = new OrderDish.OrderDishBuilder(order)
                        .price(cd.getPrice())
                        .name(cd.getName())
                        .description(cd.getDescription())
                        .isVegan(cd.isVegan())
                        .isGlutenFree(cd.isGlutenFree())
                        .preparationTime(cd.getPreparationTime())
                        .calories(cd.getCalories())
                        .dishType(cd.getDishType())
                        .quantity(d.getQuantity())
                        .build();
                total += cd.getPrice() * d.getQuantity();
                if (order.getOrderDishes() == null)
                    order.setOrderDishes(new ArrayList<>());
                order.getOrderDishes().add(orderDish);
            }
            order.setTotalPrice(total);
            order.setDateTime(new Date(System.currentTimeMillis()));
            Order savedOrder = repository.save(order);
            return new ResponseEntity<>("Order created successfully", savedOrder, ReturnType.SUCCESS);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Failed to create Order", null, ReturnType.FAILURE);
        }
    }


    @Override
    public void update(Order order) {
        logger.info("\n OrderService: Update received from the kitchen with id " + order.getId());
        repository.save(order);
    }

    public HashMap<Status, List<Order>> getPendingOrders() {
        HashMap<Status, List<Order>> map = new HashMap<>();
        map.put(Status.PREPARING, repository.findByStatus(Status.PREPARING));
        map.put(Status.READY, repository.findByStatus(Status.READY));
        map.put(Status.PENDING, repository.findByStatus(Status.PENDING));
        return map;
    }

    public ResponseEntity<Order> updateOrderStatus(UUID orderId, Status newStatus) {
        try {
            Order order = repository.findById(orderId).orElse(null);
            if (order == null) {
                logger.error("Order not found with ID: " + orderId);
                return new ResponseEntity<>("Order not found", null, ReturnType.FAILURE);
            }
            System.out.println(newStatus);
            order.setStatus(newStatus);
            Order updatedOrder = repository.save(order);
            logger.info("Order status updated successfully for Order ID: " + orderId);
            return new ResponseEntity<>("Order status updated successfully", updatedOrder, ReturnType.SUCCESS);
        } catch (Exception e) {
            logger.error("Error updating order status: " + e.getMessage());
            return new ResponseEntity<>("Failed to update order status", null, ReturnType.FAILURE);
        }
    }

    public ResponseEntity<Order> updatePaymentStatus(UUID orderId, PaymentStatus newPaymentStatus) {
        try {
            Order order = repository.findById(orderId).orElse(null);
            if (order == null) {
                logger.error("Order not found with ID: " + orderId);
                return new ResponseEntity<>("Order not found", null, ReturnType.FAILURE);
            }
            System.out.println(newPaymentStatus);
            order.setPaymentStatus(newPaymentStatus);
            Order updatedOrder = repository.save(order);
            logger.info("Payment status updated successfully for Order ID: " + orderId);
            return new ResponseEntity<>("Payment status updated successfully", updatedOrder, ReturnType.SUCCESS);
        } catch (Exception e) {
            logger.error("Error updating payment status: " + e.getMessage());
            return new ResponseEntity<>("Failed to update payment status", null, ReturnType.FAILURE);
        }
    }


}
