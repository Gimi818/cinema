package com.cinema.user;

import com.cinema.emailSender.ConfirmationEmail;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@AllArgsConstructor
@Log4j2
public class ConfirmUser {
    private final UserRepository repository;
    private final ConfirmationEmail confirmationEmail;


    public String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }
    public String generateConfirmationLink(User user) {

        return "http://localhost:8080/users/confirm?token=" + user.getConfirmationToken();
    }
    public void sendConfirmationEmail(User user) {
        try {
            confirmationEmail.sendConfirmationEmail(user.getEmail(), generateConfirmationLink(user));
        } catch (MessagingException e) {
            log.error("Error sending the confirmation email.", e);
        }
    }
    public void confirmUserAccount(String token) {

        User user = repository.findByConfirmationToken(token);

        if (user != null) {
            user.setAccountType(AccountType.ACTIVE);
            user.setConfirmationToken(null);

            repository.save(user);
            log.info("Email confirmed");
        }
    }


}
