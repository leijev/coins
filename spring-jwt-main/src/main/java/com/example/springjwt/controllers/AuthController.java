package com.example.springjwt.controllers;

import com.example.springjwt.filters.utils.TokenService;
import com.example.springjwt.models.Role;
import com.example.springjwt.models.User;
import com.example.springjwt.repositories.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    @Autowired
    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = null;
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return ResponseEntity.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Success login " + tokenService.generate(user));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You not registered!");
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Choose another username!");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        User usr = User.builder()
                .username(username)
                .password(user.getPassword())
                .roles(roles)
                .coins(999)
                .build();

        try {
            userRepository.save(usr);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Success registration not try to login :)");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
     }
}
