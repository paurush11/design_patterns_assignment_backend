package com.example.finalProjectDesignPatterns.controller;



import com.example.finalProjectDesignPatterns.config.AuthResponse;
import com.example.finalProjectDesignPatterns.dto.AuthenticationDto;

import com.example.finalProjectDesignPatterns.entity.User;
import com.example.finalProjectDesignPatterns.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    private ResponseEntity<AuthResponse> createUser(@RequestBody AuthenticationDto authenticationDto){
        return ResponseEntity.ok(userService.createUser(authenticationDto));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    private ResponseEntity authenticate(@RequestBody AuthenticationDto authenticationDto){
        AuthResponse authResponse = userService.authenticate(authenticationDto);
        if(authResponse.getToken().equals("Invalid UserName or Email")){
            System.out.println("Invalid UserName or Email");
            return new ResponseEntity<>("Invalid UserName or Email", HttpStatus.BAD_REQUEST);
        }else if (authResponse.getToken().equals("User does not exist")) {
            System.out.println("User does not exist");
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }else if (authResponse.getToken().equals("User is not Authenticated")) {
            System.out.println("User is not Authenticated");
            return new ResponseEntity<>("User is not Authenticated", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.authenticate(authenticationDto));
    }

}
