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
public class EmailAndPasswordAuthentication extends AuthenticationTemplate{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Logger log;

    @Override
    protected boolean verifyCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        User user = userRepository.findByEmail(authenticationDto.getEmail());

        return user != null && passwordEncoder.matches(authenticationDto.getPassword(), user.getPassword());
    }

    @Override
    protected boolean validateCredentials(AuthenticationDto authenticationDto) {
        // Validate credentials against the user database
        String email = authenticationDto.getEmail();
        String password = authenticationDto.getPassword();
        boolean validEmail = StringUtils.hasLength(email) && email.contains("@")
                && email.contains(".") && email.length() >= 5;
        boolean validPassword =
                StringUtils.hasLength(password) && password.length() >= 8
                        && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
        return validEmail && validPassword;
    }

    @Override
    protected boolean onSuccess(AuthenticationDto authenticationDto) {
        // Send email to the user
        log.info("user with email " + authenticationDto.getEmail() + " logged in successfully");
        return true;
    }

    @Override
    protected void onFailure(AuthenticationDto authenticationDto) {
        // Send email to the user
        log.info("user with email " + authenticationDto.getEmail() + " failed to log in");
    }

}
