package com.example.finalProjectDesignPatterns.decorators;

import java.util.List;

public class ToppingDecorator extends DishDecorator{

    private List<String> toppings;

    public ToppingDecorator(BaseDishAPI baseDishDecorator, List<String> toppings) {
        super(baseDishDecorator);
        this.toppings = toppings;
    }


    @Override
    public String getDescription() {
        String toppingList = String.join(", ", toppings);
        return super.getDescription() + " topped with " + toppingList;

    }

    @Override
    public double getPrice() {
        return super.getPrice() + 1.0 * toppings.size();
    }

    @Override
    public int getPreparationTime() {
        return super.getPreparationTime() +2;
    }

    @Override
    public int getCalories() {
        return super.getCalories() + 10 * toppings.size();
    }

}
