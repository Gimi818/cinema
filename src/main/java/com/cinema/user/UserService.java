package com.cinema.user;

import com.cinema.emailSender.ConfirmationEmail;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import com.cinema.user.exception.EmailAlreadyExistsException;
import com.cinema.user.exception.NotSamePasswordException;
import com.cinema.user.exception.UserNotFoundByIdException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository repository;
    private final ConfirmationEmail confirmationEmail;
    private final UserMapper userMapper;

    @Transactional
    public User registration(UserRequestDto requestDto) {
        existByMail(requestDto);

        passwordValidation(requestDto);

        log.info("Saving user {}", requestDto.email());

        User user = createUser(requestDto);

        repository.save(user);

        log.info("Saved user");

        sendConfirmationEmail(user);
        log.info("Sent confirmation email");

        return user;
    }

    public UserResponseDto findById(Long userId) {
        log.info("Finding user with ID {}", userId);
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));
        log.info("Found user {}", user);
        return userMapper.entityToDto(user);
    }

    public void sendConfirmationEmail(User user) {
        try {
            confirmationEmail.sendConfirmationEmail(user.getEmail(), generateConfirmationLink(user));
        } catch (MessagingException e) {
            log.error("Error sending the confirmation email.", e);
        }
    }

    public void passwordValidation(UserRequestDto requestDto) {
        if (!requestDto.password().equals(requestDto.repeatedPassword())) {
            throw new NotSamePasswordException();
        }
    }

    public void existByMail(UserRequestDto requestDto) {
        if (repository.existsByEmail(requestDto.email())) {
            throw new EmailAlreadyExistsException(requestDto.email());
        }
    }

    private String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }

    private User createUser(UserRequestDto requestDto) {
        return User.builder()
                .lastName(requestDto.lastName())
                .firstName(requestDto.firstName())
                .password(requestDto.password())
                .accountType(AccountType.UNCONFIRMED)
                .confirmationToken(generateConfirmationToken())
                .email(requestDto.email())
                .role(requestDto.role())
                .build();
    }

    private String generateConfirmationLink(User user) {

        return "http://localhost:8080/users/confirm?token=" + user.getConfirmationToken();
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

    public boolean authenticate(String email, String password) {

        User user = repository.findByEmail(email).orElseThrow();

        return user != null && user.getPassword().equals(password);
    }

}


