package org.example.springsecurity.auth.controller.Test;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public Map<String,String> getUserInfo() {
        return Map.of("message", "User Information");
    }

    @PostMapping
    public Map<String,String> updateUser() {
        return Map.of("message", "User Updated");
    }

    @DeleteMapping
    public String deleteUser() {
        return "User Deleted";
    }
}