package com.example.finalProjectDesignPatterns.dto;

public enum ReturnType {
    SUCCESS,
    FAILURE;

    public enum DishType {
        APPETIZER, ENTREE, DESSERT, DRINK,COMBO;

        public String toString() {
            return switch (this) {
                case APPETIZER -> "Appetizer";
                case ENTREE -> "Main Course";
                case DESSERT -> "Dessert";
                case DRINK -> "Beverage";
                case COMBO -> "Combo dish";
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
