package com.example.finalProjectDesignPatterns.dto;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@lombok.Data
@NoArgsConstructor
public class DishInput {

    private UUID id;
    private String name;
    private String description;
    private double price;
    private boolean isVegan;
    private boolean isGlutenFree;
    private int preparationTime;
    private int calories;
    private ReturnType.DishType dishType;
    private int quantity;
    private boolean isCustomized;
    private int cheesePreference;
    private String extraMeat;
    private List<String> toppings;
    private List<UUID> dishes;
}
