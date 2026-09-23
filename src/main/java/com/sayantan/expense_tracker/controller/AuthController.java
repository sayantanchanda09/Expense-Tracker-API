package com.sayantan.expense_tracker.controller;

import com.sayantan.expense_tracker.model.User;
import com.sayantan.expense_tracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User request) {
        return authService.registerUser(request.getUsername(), request.getPassword());
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {
        return authService.login(request.getUsername(),request.getPassword());
    }
}
