package com.cinema.user;

import com.cinema.emailSender.ConfirmationEmailFacade;
import com.cinema.user.userEnum.AccountType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
class ConfirmUser {
    private final UserRepository repository;
    private final ConfirmationEmailFacade confirmationEmail;
    @Value("${confirmation.link}")
    private String confirmationLink;

    public String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }

    public String generateConfirmationLink(User user) {
        return confirmationLink + user.getConfirmationToken();
    }

    public void sendConfirmationEmail(User user) {
        try {
            confirmationEmail.sendConfirmationEmail(user.getEmail(), generateConfirmationLink(user));
        } catch (MessagingException e) {
            log.error("Error sending the confirmation email.", e);
        }
    }

    public void confirmUserAccount(String token) {

        repository.findByConfirmationToken(token).ifPresent(user -> {
            user.setAccountType(AccountType.ACTIVE);
            user.setConfirmationToken(null);

            repository.save(user);
            log.info("Email confirmed");
        });

    }
}
