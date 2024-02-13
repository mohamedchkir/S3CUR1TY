package org.example.springsecurity.auth.controller.Test;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping
    public String getUserInfo() {
        return "User Information";
    }

    @PostMapping
    public String updateUser() {
        return "User Updated";
    }

    @DeleteMapping
    public String deleteUser() {
        return "User Deleted";
    }
}