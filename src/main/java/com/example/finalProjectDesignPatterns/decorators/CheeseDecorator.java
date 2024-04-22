package com.example.finalProjectDesignPatterns.decorators;

public class CheeseDecorator extends DishDecorator{

    private int quantity;
    public CheeseDecorator(BaseDishAPI baseDishDecorator, int quantity) {
        super(baseDishDecorator);
        this.quantity = quantity;
    }

    @Override
    public String getDescription() {
        if(quantity == 1)
            return super.getDescription() + " with   cheese";
        else if(quantity == 2)
            return super.getDescription() + " with  extra  cheese";
        else
            return super.getDescription();

    }

    @Override
    public double getPrice() {
        if(quantity == 1)
            return super.getPrice() + 1.0;
        else if(quantity == 2)
            return super.getPrice() + 1.4;
        else
            return super.getPrice();
    }

    @Override
    public boolean isVegan() {
        return false;
    }



    @Override
    public int getCalories() {
        if(quantity == 1)
            return super.getCalories() + 10;
        else if(quantity == 2)
            return super.getCalories() + 20;
        else
             return super.getCalories();
    }



}
