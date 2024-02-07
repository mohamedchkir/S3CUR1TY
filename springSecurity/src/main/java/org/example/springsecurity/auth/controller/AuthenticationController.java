package org.example.springsecurity.auth.controller;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity.auth.request.AuthenticationRequest;
import org.example.springsecurity.auth.request.RegisterRequest;
import org.example.springsecurity.auth.response.AuthenticationResponse;
import org.example.springsecurity.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        return ResponseEntity.ok(authenticationService.register(request));
    }
     @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
         return ResponseEntity.ok(authenticationService.authenticate(request));

    }

}
