package com.example.finalProjectDesignPatterns.entity;


import com.example.finalProjectDesignPatterns.decorators.BaseDishAPI;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.strategies.PricingStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

@lombok.Data
@Entity
@Table(name = "dish")
public class Dish implements BaseDishAPI {

    @Transient
    @JsonIgnore
    private PricingStrategy pricingStrategy;

    @Transient
    @JsonIgnore
    private DecimalFormat df = new DecimalFormat("#.##");

    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "is_vegan")
    private boolean isVegan;

    @Column(name = "is_gluten_free")
    private boolean isGlutenFree;

    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "calories")
    private int calories;

    @Column(name = "dish_type")
    @Enumerated(EnumType.STRING)
    private ReturnType.DishType dishType;

    @Column(name = "cheese_preference")
    private int cheesePreference;

    @Column(name = "extra_meat")
    private String extraMeat;

    @ElementCollection
    @CollectionTable(name = "dish_toppings", joinColumns = @JoinColumn(name = "dish_id"))
    @Column(name = "topping")
    private List<String> toppings;

    public double getPrice() {
        if(pricingStrategy == null){
            return Double.parseDouble(df.format(price));
        }
        return Double.parseDouble(df.format(pricingStrategy.calculatePrice(price)));
    }




    private Dish() {
    }

    public static class DishBuilder {
        private String name;
        private String description;
        private double price;
        private boolean isVegan;
        private boolean isGlutenFree;
        private int preparationTime;
        private int calories;
        private ReturnType.DishType dishType;
        private int cheesePreference;
        private String extraMeat;
        private List<String> toppings;
        private PricingStrategy pricingStrategy;


        public DishBuilder setVegan(boolean vegan) {
            isVegan = vegan;
            return this;
        }

        public DishBuilder setGlutenFree(boolean glutenFree) {
            isGlutenFree = glutenFree;
            return this;
        }

        public DishBuilder setCheesePreference(int cheesePreference) {
            this.cheesePreference = cheesePreference;
            return this;
        }

        public DishBuilder setExtraMeat(String extraMeat) {
            this.extraMeat = extraMeat;
            return this;
        }

        public DishBuilder setToppings(List<String> toppings) {
            this.toppings = toppings;
            return this;
        }

        public DishBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public DishBuilder(String name, double price) {
            this.name = name;
            this.price = price;
        }



        public DishBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public DishBuilder setPreparationTime(int preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        public DishBuilder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public DishBuilder setDishType(ReturnType.DishType dishType) {
            this.dishType = dishType;
            return this;
        }

        public DishBuilder setPricingStrategy(PricingStrategy pricingStrategy) {
            this.pricingStrategy = pricingStrategy;
            return this;
        }

        public Dish build() {
            Dish dish = new Dish();
            dish.setName(name);
            dish.setCalories(calories);
            dish.setDishType(dishType);
            dish.setDescription(description);
            dish.setCheesePreference(cheesePreference);
            dish.setExtraMeat(extraMeat);
            dish.setPreparationTime(preparationTime);
            dish.setGlutenFree(isGlutenFree);
            dish.setVegan(this.isVegan);
            dish.setPrice(this.price);

            return dish;
        }
    }

}
