package com.example.springjwt.services;

import com.example.springjwt.models.User;
import com.example.springjwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public HttpStatus save(User user) {
        userRepository.save(user);
        return HttpStatus.OK;
    }
    public HttpStatus updateCoins(User user, Integer coins) {
        int c = user.getCoins();
        user.setCoins(c - coins);
        try {
            userRepository.save(user);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
