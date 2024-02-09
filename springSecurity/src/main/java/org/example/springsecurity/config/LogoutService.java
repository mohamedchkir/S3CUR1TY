package org.example.springsecurity.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springsecurity.repository.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authorizationHeader.substring(7);
        tokenRepository.findByToken(jwt)
                .ifPresent(token -> {
                    token.setRevoked(true);
                    token.setExpired(true);
                    tokenRepository.save(token);
                });

    }
}
