package com.example.finalProjectDesignPatterns.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationDto {

    private String username;

    private String password;

    private String email;

    private String fingerPrint;

    private String userInput;

    private String phone;

    private Role role;

    private String token;


}
