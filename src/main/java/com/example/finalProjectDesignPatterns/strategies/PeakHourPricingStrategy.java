package com.example.finalProjectDesignPatterns.strategies;

public class PeakHourPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(double price) {
        return price * 1.2;
    }
}
