package com.example.finalProjectDesignPatterns.controller;


import com.example.finalProjectDesignPatterns.Enum.PricingStrategy;
import com.example.finalProjectDesignPatterns.config.JwtService;
import com.example.finalProjectDesignPatterns.decorators.BaseDishAPI;
import com.example.finalProjectDesignPatterns.dto.DishInput;
import com.example.finalProjectDesignPatterns.dto.PricingStrategyRequest;
import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.services.MenuService;
import com.example.finalProjectDesignPatterns.services.UserService;
import com.example.finalProjectDesignPatterns.strategies.HappyHourPricingStrategy;
import com.example.finalProjectDesignPatterns.strategies.PeakHourPricingStrategy;
import com.example.finalProjectDesignPatterns.strategies.RegularPricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/fullMenu",  method =RequestMethod.GET)
    public ResponseEntity<List<BaseDishAPI>> getMenu(){
        ResponseEntity<List<BaseDishAPI>> response = new ResponseEntity<>();
        List<BaseDishAPI> dishes = menuService.getDishes();
        if(dishes == null){
            response.setMessage("No dishes found, please add the dish and check back");
            response.setResponseStatus(ReturnType.SUCCESS);
        }else{
            response.setData(dishes);
            response.setResponseStatus(ReturnType.SUCCESS);
        }

        return response;
    }

    @RequestMapping(value = "/addDish", method = RequestMethod.POST)
    public ResponseEntity<BaseDishAPI> addDish(@RequestBody DishInput dishInput){
        return menuService.addDish(dishInput,false);
    }

    @RequestMapping(value = "pricingStrategy", method = RequestMethod.POST)
    public ResponseEntity<List<BaseDishAPI>> updateMenu(@RequestBody PricingStrategyRequest pricingStrategyRequest){

        List<BaseDishAPI> dishes;
        System.out.println(pricingStrategyRequest.getStrategy());
        if(pricingStrategyRequest.getStrategy() == null || pricingStrategyRequest.getStrategy().isEmpty()){
            System.out.println(pricingStrategyRequest);
            return new ResponseEntity<>("Invalid strategy", null, ReturnType.FAILURE);
        }else if(pricingStrategyRequest.getStrategy().equalsIgnoreCase(String.valueOf(PricingStrategy.HAPPYHOUR))){
            menuService.updatePricingStrategy(new HappyHourPricingStrategy());
        }else if(pricingStrategyRequest.getStrategy().equalsIgnoreCase(String.valueOf(PricingStrategy.REGULAR))){
            menuService.updatePricingStrategy(new RegularPricingStrategy());
        }else if(pricingStrategyRequest.getStrategy().equalsIgnoreCase(String.valueOf(PricingStrategy.PEAKHOUR))){
            menuService.updatePricingStrategy(new PeakHourPricingStrategy());
        }else{
            return new ResponseEntity<>("Invalid strategy, valid strategies are happyhour, regular and peakhour", null, ReturnType.FAILURE);
        }
        dishes = menuService.getDishes();
        return new ResponseEntity<>("Prices updated successfully", dishes, ReturnType.SUCCESS);
    }
}
