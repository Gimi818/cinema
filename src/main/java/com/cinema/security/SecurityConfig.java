package com.cinema.security;

import com.cinema.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration

public class SecurityConfig  {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        configurer -> configurer
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/films/**",
                                        "/screenings/**",
                                        "/seats/**",
                                        "/codes",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/users/**"

                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,
                                        "/films/**",
                                        "/screenings/add/**",
                                        "/screenings/**",
                                        "/users/**",
                                        "/book/**"
                                ).permitAll()
//                                .requestMatchers(HttpMethod.POST,
//                                        "films/add",
//                                        "screenings/**")
//                                .hasAnyAuthority("ADMIN")
//                                .requestMatchers(
//                                        HttpMethod.DELETE,
//                                        "/films/**",
//                                        "/screenings/**"
//                                ).hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }


}
