package org.example.springsecurity.auth.controller.Test;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/hello")
public class HelloController {
    @GetMapping
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello World");
    }
}
