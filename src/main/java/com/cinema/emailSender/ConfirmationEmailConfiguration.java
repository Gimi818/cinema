package com.cinema.emailSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmationEmailConfiguration {
    @Bean
    public ConfirmationEmailFacade confirmationEmailFacade(final ConfirmationEmail confirmationEmail) {
        return confirmationEmail;
    }
}

