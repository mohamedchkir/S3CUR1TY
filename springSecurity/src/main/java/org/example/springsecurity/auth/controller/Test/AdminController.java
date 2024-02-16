package org.example.springsecurity.auth.controller.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {

    @GetMapping
    public Map<String,String> getAdminInfo() {
        return Map.of("message", "Admin Information");
    }

    @PostMapping
    public Map<String, String> updateAdmin() {
        return Map.of("message", "Admin Information");

    }
}
