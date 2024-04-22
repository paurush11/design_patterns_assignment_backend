package com.example.finalProjectDesignPatterns.entity;

import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class OrderDish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, name = "id", nullable = false)
    private UUID id;


    @Column(name = "price")
    private double price;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_vegan")
    private boolean isVegan;

    @Column(name = "is_gluten_free")
    private boolean isGlutenFree;

    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "calories")
    private int calories;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "dish_type")
    private ReturnType.DishType dishType;

    @JoinColumn(name = "order_id")
    @ManyToOne
    @JsonIgnore
    private Order order;


    private  OrderDish() {
    }

    public static class OrderDishBuilder{

        private double price;
        private String name;
        private String description;
        private boolean isVegan;
        private boolean isGlutenFree;
        private int preparationTime;
        private int calories;
        private int quantity;
        private final Order order;

        private ReturnType.DishType dishType;

        public OrderDishBuilder(Order order){
            this.order = order;
        }

        public OrderDishBuilder price(double price){
            this.price = price;
            return this;
        }

        public OrderDishBuilder name(String name){
            this.name = name;
            return this;
        }

        public OrderDishBuilder description(String description){
            this.description = description;
            return this;
        }

        public OrderDishBuilder isVegan(boolean isVegan){
            this.isVegan = isVegan;
            return this;
        }

        public OrderDishBuilder isGlutenFree(boolean isGlutenFree){
            this.isGlutenFree = isGlutenFree;
            return this;
        }

        public OrderDishBuilder preparationTime(int preparationTime){
            this.preparationTime = preparationTime;
            return this;
        }

        public OrderDishBuilder calories(int calories){
            this.calories = calories;
            return this;
        }

        public OrderDishBuilder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public OrderDishBuilder dishType(ReturnType.DishType dishType){
            this.dishType = dishType;
            return this;
        }

        public OrderDish build(){
            OrderDish orderDish = new OrderDish();
            orderDish.setOrder(order);
            orderDish.setPrice(price);
            orderDish.setName(name);
            orderDish.setDescription(description);
            orderDish.setVegan(isVegan);
            orderDish.setGlutenFree(isGlutenFree);
            orderDish.setPreparationTime(preparationTime);
            orderDish.setCalories(calories);
            orderDish.setQuantity(quantity);
            return orderDish;
        }

    }
}
