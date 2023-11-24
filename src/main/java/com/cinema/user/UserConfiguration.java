package com.cinema.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
class UserConfiguration {

    @Bean
    public UserFacade userFacade(@Lazy final UserService userService) {
        return userService;
    }
}
