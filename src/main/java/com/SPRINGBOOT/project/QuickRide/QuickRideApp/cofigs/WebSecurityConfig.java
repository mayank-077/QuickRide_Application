package com.SPRINGBOOT.project.QuickRide.QuickRideApp.cofigs;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//works on security filter chain
// we will be adding things into the security filter chain
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true) // this is authentication on the basis of roles
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter; // filter chain
    private static final String[] PUBLIC_ROUTES= {"/auth/**"};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // authentication is not done by session here done by tokens -> state less -> we don't have to hold the information about each user
        //our authentication is token based -> stateless
        httpSecurity
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrfConfig -> csrfConfig.disable()) // disabling the csrf
                .authorizeHttpRequests(auth -> auth // authorizing the request
                        .requestMatchers(
                                "/swagger-ui/**",        // Allow Swagger UI
                                "/v3/api-docs/**",       // Allow API docs
                                "/swagger-ui.html",      // Swagger HTML page
                                "/actuator/**",          // Actuator endpoints if used
                                "/**"                    // Optionally allow all localhost requests
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build(); // return the security chain
    }
}