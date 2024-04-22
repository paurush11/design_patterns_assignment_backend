package com.example.finalProjectDesignPatterns.strategies;

public class HappyHourPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(double price) {
        return price * 0.8;
    }

}
