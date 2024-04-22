package com.example.finalProjectDesignPatterns.services;



import com.example.finalProjectDesignPatterns.config.AuthResponse;
import com.example.finalProjectDesignPatterns.config.JwtService;
import com.example.finalProjectDesignPatterns.dto.AuthenticationDto;
import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.entity.User;
import com.example.finalProjectDesignPatterns.repository.UserRepository;
import com.example.finalProjectDesignPatterns.template.AuthenticationTemplateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationTemplateFactory authenticationTemplateFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse createUser(AuthenticationDto authenticationDto) {
        User oldUser = userRepository.findByUsername(authenticationDto.getUsername());
        if (oldUser != null) {
            var jwtToken = jwtService.generateToken(oldUser);
            return  AuthResponse.builder().Token(jwtToken).ExistedBefore(true).build();
        }
        User user = new User();
        user.setUsername(authenticationDto.getUsername());
        user.setPassword(passwordEncoder.encode(authenticationDto.getPassword()));
        user.setEmail(authenticationDto.getEmail());
        user.setFingerPrint(authenticationDto.getFingerPrint());
        user.setRole(authenticationDto.getRole());
        user.setPhone(authenticationDto.getPhone());
        User saved = userRepository.saveAndCatch(user).getData();

        var jwtToken = jwtService.generateToken(saved);
        return AuthResponse.builder().Token(jwtToken).ExistedBefore(false).build();
    }


    public AuthResponse authenticate(AuthenticationDto authenticationDto) {
        System.out.println(authenticationDto);
        if(authenticationDto.getUsername() == null && authenticationDto.getEmail() == null){
           // case or error
            return  AuthResponse.builder().Token("Invalid UserName or Email").ExistedBefore(false).build();
        }
        User user = userRepository.findByUsernameOrEmail(authenticationDto.getUsername(), authenticationDto.getEmail());
        if (user == null) {
            // error
           return AuthResponse.builder().Token("User does not exist").ExistedBefore(false).build();
        }

        boolean result = authenticationTemplateFactory
                        .createAuthenticationTemplate(authenticationDto.getUserInput())
                        .authenticateAndAuthorize(authenticationDto);
        System.out.println(result);
        if (result) {
            authenticationDto.setToken(jwtService.generateToken(user));
            return  AuthResponse.builder().Token(authenticationDto.getToken()).ExistedBefore(true).build();
//            return new ResponseEntity<>("User authenticated", authenticationDto , ReturnType.SUCCESS);
        }

        return AuthResponse.builder().Token("User is not Authenticated").ExistedBefore(false).build();
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
