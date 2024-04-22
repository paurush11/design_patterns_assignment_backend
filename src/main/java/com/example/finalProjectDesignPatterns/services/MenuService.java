package com.example.finalProjectDesignPatterns.services;


import com.example.finalProjectDesignPatterns.decorators.BaseDishAPI;
import com.example.finalProjectDesignPatterns.dto.DishInput;
import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.entity.ComboDish;
import com.example.finalProjectDesignPatterns.entity.Dish;
import com.example.finalProjectDesignPatterns.repository.ComboDishRepository;
import com.example.finalProjectDesignPatterns.repository.DishRepository;
import com.example.finalProjectDesignPatterns.strategies.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private ComboDishRepository comboDishRepository;

    private static MenuService instance = null;
    private List<BaseDishAPI> dishes;
    private MenuService() {
    }
    public static MenuService getInstance() {
        synchronized(MenuService.class) {
            if (instance == null) {
                instance = new MenuService();
            }
        }
        return instance;
    }

    public BaseDishAPI getDish(UUID id){
        loadDishes();
        for(BaseDishAPI dish: dishes){
            if(Objects.equals(dish.getId(), id)){
                return dish;
            }
        }
        return null;
    }

    public ResponseEntity<BaseDishAPI> addDish(DishInput d, boolean isCustomized) {
        if(d == null || d.getName() == null || d.getName().isEmpty()){
            return new ResponseEntity<>("Invalid dish", null, ReturnType.FAILURE);
        }
        if(d.getDishType() == ReturnType.DishType.COMBO){
            ComboDish.ComboDishBuilder comboDishBuilder = new ComboDish.ComboDishBuilder(d.getName());
            for(UUID dishId: d.getDishes()){
                BaseDishAPI dish = getDish(dishId);
                if(dish == null || dish.getDishType() == ReturnType.DishType.COMBO){
                    return new ResponseEntity<>("Invalid dish id", null, ReturnType.FAILURE);
                }
                comboDishBuilder.addDish((Dish) dish);
            }
          return addDish(comboDishBuilder.build(), isCustomized);
        }

        Dish dish =
                new Dish.DishBuilder(d.getName(),d.getPrice())
                        .setPrice(d.getPrice())
                        .setDescription(d.getDescription())
                        .setDishType(d.getDishType())
                        .setCalories(d.getCalories())
                        .setPreparationTime(d.getPreparationTime())
                        .setGlutenFree(d.isGlutenFree())
                        .setVegan(d.isVegan())
                        .setCheesePreference(d.getCheesePreference())
                        .setExtraMeat(d.getExtraMeat())
                        .setToppings(d.getToppings())
                        .build();
        System.out.println(dish);
        return addDish(dish,false);
    }

    public ResponseEntity<BaseDishAPI> addDish(Dish dish, boolean isCustomized){
        ResponseEntity<Dish> response =dishRepository.saveAndCatch(dish);
        if(response.getResponseStatus() == ReturnType.FAILURE){
            return new ResponseEntity<>(response.getMessage(),dish, ReturnType.FAILURE);
        }else{
            dish = response.getData();
        }
        if(dishes == null){
            dishes = new ArrayList<>();
        }
        if(!isCustomized){
            dishes.add(dish);
        }
        return new ResponseEntity<>("Dish added successfully",dish, ReturnType.SUCCESS);
    }

    public ResponseEntity<BaseDishAPI> addDish(ComboDish dish, boolean isCustomized){
        ResponseEntity<ComboDish> response =comboDishRepository.saveAndCatch(dish);
        if(response.getResponseStatus() == ReturnType.FAILURE){
            return new ResponseEntity<>(response.getMessage(),dish, ReturnType.FAILURE);
        }else{
            dish = response.getData();
        }
        if(dishes == null){
            dishes = new ArrayList<>();
        }
        if(!isCustomized){
            dishes.add(dish);
        }
        return new ResponseEntity<>("Dish added successfully",dish, ReturnType.SUCCESS);

    }

    private synchronized void loadDishes(){
        try{
            if(dishes == null){
                dishes = new ArrayList<>();
                dishes.addAll(dishRepository.findAll());
                dishes.addAll(comboDishRepository.findAll());
            }
        }catch(Exception ex){
            dishes = null;
            System.out.println("Failed in fetching the dishes from database");
        }
    }



    public List<BaseDishAPI> getDishes() {
        loadDishes();
        return dishes;
    }

    public void updatePricingStrategy(PricingStrategy pricingStrategy) {
        loadDishes();
        for(BaseDishAPI dish: dishes){
            dish.setPricingStrategy(pricingStrategy);
        }
    }

    public ResponseEntity<BaseDishAPI> updateDish(BaseDishAPI dish) {
        UUID id = dish.getId();
        dishes.removeIf(d -> Objects.equals(d.getId(), id));
        ResponseEntity<ComboDish> comboDishResponse;
        ResponseEntity<Dish> dishResponse;
        if(dish.getDishType() == ReturnType.DishType.COMBO){
            comboDishResponse  =comboDishRepository.saveAndCatch((ComboDish) dish);
            if(comboDishResponse.getResponseStatus() == ReturnType.FAILURE){
                return new ResponseEntity<>(comboDishResponse.getMessage(),dish, ReturnType.FAILURE);
            }
            dish = comboDishResponse.getData();
        }else{
            dishResponse = dishRepository.saveAndCatch((Dish) dish);
            if(dishResponse.getResponseStatus() == ReturnType.FAILURE){
                return new ResponseEntity<>(dishResponse.getMessage(),dish, ReturnType.FAILURE);
            }
            dish = dishResponse.getData();
        }

        if(dishes == null){
            dishes = new ArrayList<>();
        }
        dishes.add(dish);
        return new ResponseEntity<>("Dish updated successfully",dish, ReturnType.SUCCESS);
    }



}
