package org.example.springsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.example.springsecurity.Const.enums.Permission.*;
import static org.example.springsecurity.Const.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAnyRole(ADMIN.name(), SUPER_ADMIN.name())
                        .requestMatchers("/api/v1/superAdmin/**").hasRole(SUPER_ADMIN.name())
                        .requestMatchers("/api/v1/user/**").hasAnyRole(USER.name(),ADMIN.name(),SUPER_ADMIN.name())
                        .requestMatchers(GET,"/api/v1/user/**").hasAnyAuthority(USER_READ.name(),ADMIN_READ.name(),SUPER_ADMIN_READ.name())
                        .requestMatchers(POST,"/api/v1/user/**").hasAnyAuthority(USER_WRITE.name(),ADMIN_WRITE.name(),SUPER_ADMIN_WRITE.name())
                        .requestMatchers(DELETE,"/api/v1/user/**").hasAnyAuthority(USER_DELETE.name(),ADMIN_DELETE.name(),SUPER_ADMIN_DELETE.name())
                        .requestMatchers(PUT,"/api/v1/user/**").hasAnyAuthority(USER_UPDATE.name(),ADMIN_UPDATE.name(),SUPER_ADMIN_UPDATE.name())
                        .anyRequest()
                        .authenticated())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext()));
        return httpSecurity.build();
    }


}
