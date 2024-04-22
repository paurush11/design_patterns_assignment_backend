package com.example.finalProjectDesignPatterns.decorators;

public class ExtraMeatDecorator extends DishDecorator{

    private final String description;
    public ExtraMeatDecorator(BaseDishAPI baseDishDecorator, String description) {
        super(baseDishDecorator);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " with extra " + description;
    }

    @Override
    public double getPrice() {
        if(description.equals("chicken"))
            return super.getPrice() + 3.0;
        else if(description.equals("beef"))
            return super.getPrice() + 1.5;
        else if(description.equals("pork"))
            return super.getPrice() + 2.0;
        else if(description.equals("lamb"))
            return super.getPrice() + 4.5;
        else
            return super.getPrice();
    }

    @Override
    public int getPreparationTime() {
        return super.getPreparationTime() + 3;
    }

    @Override
    public int getCalories() {
        if(description.equals("chicken"))
            return super.getCalories() + 100;
        else if(description.equals("beef"))
            return super.getCalories() + 150;
        else if(description.equals("pork"))
            return super.getCalories() + 200;
        else if(description.equals("lamb"))
            return super.getCalories() + 250;
        else
            return super.getCalories();
    }

}
