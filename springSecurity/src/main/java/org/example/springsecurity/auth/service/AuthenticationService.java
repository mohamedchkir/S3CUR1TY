package org.example.springsecurity.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springsecurity.Const.enums.Role;
import org.example.springsecurity.Const.enums.TokenType;
import org.example.springsecurity.auth.request.AuthenticationRequest;
import org.example.springsecurity.auth.request.RegisterRequest;
import org.example.springsecurity.auth.response.AuthenticationResponse;
import org.example.springsecurity.config.JwtService;
import org.example.springsecurity.dto.UserDto;
import org.example.springsecurity.entity.token.Token;
import org.example.springsecurity.entity.user.User;
import org.example.springsecurity.repository.TokenRepository;
import org.example.springsecurity.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser =userRepository.save(user);

        Map<String, Object> claims = new HashMap<>();
        String jwtToken = jwtService.generateToken(claims,user);

        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        var expirationDate = getExpirationDateFromToken(jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .expireAt(expirationDate)
                .refreshToken(refreshToken)
                .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request ) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        Map<String, Object> claims = new HashMap<>();
        String token = jwtService.generateToken(claims,user);
        revokeUserTokens(user);
        saveUserToken(user, token);
        var refreshToken = jwtService.generateRefreshToken(user);
        var expirationDate = getExpirationDateFromToken(token);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .expireAt(expirationDate)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeUserTokens(User user) {
        var tokens = tokenRepository.findAllValidByUserId(user.getId());
        if (tokens.isEmpty()) return;

        tokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(tokens);
    }
    private void saveUserToken(User savedUser, String jwtToken) {
        var token = Token.builder()
                .user(savedUser)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }

    private Date getExpirationDateFromToken(String token) {
        return jwtService.getFormattedExpirationDateFromToken(token);
    }

    public void refresh(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authorizationHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
            if (jwtService.validateToken(refreshToken, user)) {
                var accessToken = jwtService.generateToken(new HashMap<>(), user);
                revokeUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .expireAt(getExpirationDateFromToken(accessToken))
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }

        }
    }

    }

