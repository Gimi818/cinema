package com.cinema.user;

import com.cinema.emailSender.ConfirmationEmail;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.exception.EmailAlreadyExistsException;
import com.cinema.user.exception.NotSamePasswordException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {


    private final UserRepository repository;
    private final UserMapper mapper;
    private final ConfirmationEmail confirmationEmail;


    public User createUser(UserRequestDto requestDto) {

        if (repository.existsByEmail(requestDto.email())) {
            throw new EmailAlreadyExistsException(requestDto.email());
        }
        if (!requestDto.password().equals(requestDto.repeatedPassword())) {
            throw new NotSamePasswordException();
        }
        log.info("Saving user {}", requestDto.email());
        User user = repository.save(mapper.dtoToEntity(requestDto));
        log.info("Saved user");

        String confirmationLink = generateConfirmationLink(user.getEmail());
        try {
            confirmationEmail.sendConfirmationEmail(user.getEmail(), confirmationLink);
        } catch (MessagingException e) {

            log.error("Błąd podczas wysyłania e-maila potwierdzającego.", e);
        }

        return user;
    }

    private String generateConfirmationLink(String email) {
        String confirmationToken = UUID.randomUUID().toString(); // Generuj losowy token potwierdzający

        String confirmationLink = "https://example.com/confirm?token=" + confirmationToken;
        return confirmationLink;
    }

}

