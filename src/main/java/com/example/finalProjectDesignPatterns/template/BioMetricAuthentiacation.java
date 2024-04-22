package com.example.finalProjectDesignPatterns.template;


import com.example.finalProjectDesignPatterns.dto.AuthenticationDto;

import com.example.finalProjectDesignPatterns.entity.User;
import com.example.finalProjectDesignPatterns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BioMetricAuthentiacation extends AuthenticationTemplate {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected boolean validateCredentials(AuthenticationDto authenticationDto) {
        return authenticationDto.getFingerPrint() != null &&
                authenticationDto.getFingerPrint().length() >= 30 &&
                authenticationDto.getFingerPrint().matches(".*[0-9].*");
    }

    @Override
    protected boolean verifyCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        User user = userRepository.findByUsername(authenticationDto.getUsername());
        return user != null && user.getFingerPrint().equals(authenticationDto.getFingerPrint());
    }

    @Override
    protected boolean onSuccess(AuthenticationDto authenticationDto) {
        // Send email to the user
        return true;
    }

    @Override
    protected void onFailure(AuthenticationDto authenticationDto) {
        // Send email to the user
    }
}
