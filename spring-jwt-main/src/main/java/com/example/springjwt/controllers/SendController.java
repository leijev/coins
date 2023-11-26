package com.example.springjwt.controllers;

import com.example.springjwt.models.User;
import com.example.springjwt.repositories.UserRepository;
import com.example.springjwt.requests.SendRequest;
import com.example.springjwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/transactions")
public class SendController {
    private ArrayList<String> history = new ArrayList<>();
    private UserService userService;

    @Autowired
    public SendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/history")
    public ResponseEntity<?> history() {
        if (history.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(history.get(history.size() - 1));
        }
        return null;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody SendRequest sendRequest, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).get();
        if (user.getCoins() >= sendRequest.getCoins()) {
            history.add(user.getUsername() + " sent " + sendRequest.getCoins() + " to " + sendRequest.getReceiver());

            userService.updateCoins(user, sendRequest.getCoins());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("you dont have coins!");
    }
}
