package com.example.finalProjectDesignPatterns.decorators;



import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.strategies.PricingStrategy;

import java.util.UUID;

public interface BaseDishAPI {
        UUID getId();
        String getName();
        String getDescription();
        double getPrice();
        boolean isVegan();
        boolean isGlutenFree();
        int getPreparationTime();
        int getCalories();
        ReturnType.DishType getDishType();
        void setPricingStrategy(PricingStrategy pricingStrategy);

}
