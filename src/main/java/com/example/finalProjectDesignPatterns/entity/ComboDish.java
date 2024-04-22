package com.example.finalProjectDesignPatterns.entity;

import com.example.finalProjectDesignPatterns.decorators.BaseDishAPI;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.strategies.PricingStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@lombok.Data
@Entity(name = "combo_dish")
public class ComboDish implements BaseDishAPI {

    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "combo_dish_dish",
            joinColumns = @JoinColumn(name = "combo_dish_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    @JsonIgnore
    private List<Dish> dishes;

    @Transient
    @JsonIgnore
    private PricingStrategy pricingStrategy;

    @Transient
    @JsonIgnore
    private DecimalFormat df = new DecimalFormat("#.##");

    @Column(name = "name", nullable = false)
    private  String name;



    private ComboDish(String name) {
        this.dishes = new ArrayList<>();
        this.name = name;
    }



    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    @Override
    public String getName() {
        return this.name;
    }



    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder("Combo meal with the following dishes: ");
        for (BaseDishAPI dish : dishes) {
            sb.append(dish.getName());
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public double getPrice() {
        double price = 0;
        for (BaseDishAPI dish : dishes) {
            price += dish.getPrice();
        }
        price = price * 0.8;
        if(pricingStrategy != null) {
            price = pricingStrategy.calculatePrice(price);
        }
        return Double.parseDouble(df.format(price));
    }

    @Override
    public boolean isVegan() {
        for (BaseDishAPI dish : dishes) {
            if (!dish.isVegan()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isGlutenFree() {
        for (BaseDishAPI dish : dishes) {
            if (!dish.isGlutenFree()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getPreparationTime() {
        int time = 0;
        for (BaseDishAPI dish : dishes) {
            time = Math.max(dish.getPreparationTime(), time);
        }
        return time;
    }

    @Override
    public int getCalories() {
        int calories = 0;
        for (BaseDishAPI dish : dishes) {
            calories += dish.getCalories();
        }
        return calories;
    }


    @Override
    public ReturnType.DishType getDishType() {
        return ReturnType.DishType.COMBO;
    }

    private ComboDish() {
    }

    public static class ComboDishBuilder{
        private final String name;

        private final List<Dish> dishes;

        private PricingStrategy pricingStrategy;

        public ComboDishBuilder(String name) {
            this.name = name;
            this.dishes = new ArrayList<>();
        }

        public void addDish(Dish dish){
            this.dishes.add(dish);
        }

        public ComboDishBuilder setPricingStrategy(PricingStrategy pricingStrategy){
            this.pricingStrategy = pricingStrategy;
            return this;
        }

        public ComboDish build(){
            ComboDish comboDish = new ComboDish(this.name);
            comboDish.setDishes(this.dishes);
            comboDish.setPricingStrategy(this.pricingStrategy);
            return comboDish;
        }
    }
}

