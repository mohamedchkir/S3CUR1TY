package org.example.springsecurity.auth.controller.Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {

    @GetMapping
    public String getAdminInfo() {
        return "Admin Information";
    }

    @PostMapping
    public String updateAdmin() {
        return "Admin Updated";
    }
}
