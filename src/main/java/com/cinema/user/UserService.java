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
    private final UserMapper userMapper;
    private final ConfirmUser confirmUser;

    @Transactional
    public User registration(UserRequestDto requestDto) {
        existByMail(requestDto);

        passwordValidation(requestDto);

        log.info("Saving user {}", requestDto.email());

        User user = createUser(requestDto);

        repository.save(user);

        log.info("Saved user");

        confirmUser.sendConfirmationEmail(user);
        log.info("Sent confirmation email");

        return user;
    }

    public UserResponseDto findById(Long userId) {
        log.info("Finding user with ID {}", userId);
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));
        log.info("Found user {}", user);
        return userMapper.entityToDto(user);
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



    private User createUser(UserRequestDto requestDto) {
        return User.builder()
                .lastName(requestDto.lastName())
                .firstName(requestDto.firstName())
                .password(requestDto.password())
                .accountType(AccountType.UNCONFIRMED)
                .confirmationToken(confirmUser.generateConfirmationToken())
                .email(requestDto.email())
                .role(requestDto.role())
                .build();
    }

    public boolean authenticate(String email, String password) {

        User user = repository.findByEmail(email).orElseThrow();

        return user != null && user.getPassword().equals(password);
    }

}


