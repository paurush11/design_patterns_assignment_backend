package com.example.finalProjectDesignPatterns.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthResponse {
    private String Token;
    private Boolean ExistedBefore;
}
