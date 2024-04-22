package com.example.finalProjectDesignPatterns.template;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationTemplateFactory {

    @Autowired
    private BioMetricAuthentiacation bioMetricAuthentiacation;

    @Autowired
    private UserNameAndPasswordAuthentation userNameAndPasswordAuthentation;

    @Autowired
    private EmailAndPasswordAuthentication emailAndPasswordAuthentication;


    public AuthenticationTemplate createAuthenticationTemplate(String userInput) {
        return switch (userInput) {
            case "username-password" -> userNameAndPasswordAuthentation;
            case "email-password" -> emailAndPasswordAuthentication;
            case "biometric" -> bioMetricAuthentiacation;
            default -> throw new IllegalArgumentException("Invalid authentication type");
        };
    }

}
