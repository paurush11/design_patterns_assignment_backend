package com.example.finalProjectDesignPatterns.template;


import com.example.finalProjectDesignPatterns.dto.AuthenticationDto;
import com.example.finalProjectDesignPatterns.entity.User;
import com.example.finalProjectDesignPatterns.logger.Logger;
import com.example.finalProjectDesignPatterns.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserNameAndPasswordAuthentation extends AuthenticationTemplate{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Logger log;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected boolean verifyCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        User user = userRepository.findByUsername(authenticationDto.getUsername());
        System.out.println(user.getPassword());
        return user != null && passwordEncoder.matches(authenticationDto.getPassword(), user.getPassword());
    }

    @Override
    protected boolean validateCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        String userName = authenticationDto.getUsername();
        String password = authenticationDto.getPassword();
        System.out.println(password);
        boolean validUserName = StringUtils.hasLength(userName) && userName.length() >= 8
                && userName.matches(".*[A-Z].*") && userName.matches(".*[a-z].*");
        boolean validPassword =
                StringUtils.hasLength(password) && password.length() >= 8
                        && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*[0-9].*") && password.matches(".*[!@#$%^&*].*");
        return validUserName && validPassword;
    }

    @Override
    protected boolean onSuccess(AuthenticationDto authenticationDto) {
        // Send email to the user
        log.info("user with userName  " + authenticationDto.getUsername() + " logged in successfully");
        return true;
    }

    @Override
    protected void onFailure(AuthenticationDto authenticationDto) {
        // Send email to the user
        log.info("user with userName " + authenticationDto.getUsername() + " failed to log in");
    }
}
