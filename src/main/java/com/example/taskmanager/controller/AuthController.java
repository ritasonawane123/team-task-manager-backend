package com.example.taskmanager.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;

import jakarta.validation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/auth")


public class AuthController {
	@Autowired
    private UserRepository repo;

    @PostMapping("/signup")
    public User signup(@Valid @RequestBody User user) {
        return repo.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {

        return repo.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(user.getEmail()) &&
                             u.getPassword().equals(user.getPassword()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

}
