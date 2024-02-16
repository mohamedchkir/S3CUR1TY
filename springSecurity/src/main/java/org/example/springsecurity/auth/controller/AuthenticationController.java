package org.example.springsecurity.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springsecurity.auth.request.AuthenticationRequest;
import org.example.springsecurity.auth.request.RegisterRequest;
import org.example.springsecurity.auth.response.AuthenticationResponse;
import org.example.springsecurity.auth.service.AuthenticationService;
import org.example.springsecurity.dto.UserDto;
import org.example.springsecurity.entity.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

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

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        authenticationService.refresh(request,response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getAuthenticatedUser (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User user) {

            return ResponseEntity.ok(
                    UserDto.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .role(user.getRole().name())
                            .permissions(user.getRole().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                            .build()
            );
        }

        return ResponseEntity.badRequest().build();
    }

}
