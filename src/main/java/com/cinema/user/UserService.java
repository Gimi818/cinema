package com.cinema.user;

import com.cinema.common.exception.exceptions.AlreadyExistException;
import com.cinema.common.exception.exceptions.NotFoundException;
import com.cinema.common.exception.exceptions.PasswordConflictException;
import com.cinema.user.dto.CreatedUserDto;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import com.cinema.user.encoder.PasswordEncoderService;
import com.cinema.user.userEnum.AccountType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.cinema.user.UserService.ErrorMessages.*;

@Service
@AllArgsConstructor
@Log4j2
class UserService implements UserFacade {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final ConfirmUser confirmUser;
    private final PasswordEncoderService passwordEncoderService;


    @Transactional
    public CreatedUserDto registration(UserRequestDto requestDto) {

        existByMail(requestDto);
        passwordValidation(requestDto);

        User user = createUser(requestDto);

        repository.save(user);

        log.info("Saved user {}", requestDto.email());

        confirmUser.sendConfirmationEmail(user);
        log.info("Sent confirmation email");

        return userMapper.createdEntityToDto(user);

    }

    private User createUser(UserRequestDto requestDto) {
        return User.builder()
                .lastName(requestDto.lastName())
                .firstName(requestDto.firstName())
                .password(passwordEncoderService.encodePassword(requestDto.password()))
                .accountType(AccountType.UNCONFIRMED)
                .confirmationToken(confirmUser.generateConfirmationToken())
                .email(requestDto.email())
                .role(requestDto.role())
                .build();
    }


    public User findByUuid(UUID userUuid) {
        User user = repository.findByUuid(userUuid).orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID, userUuid));
        log.info("Found user with UUID {}", user.getUuid());
        return user;
    }

    public UserResponseDto findUserByUuid(UUID userUuid) {
        User user = repository.findByUuid(userUuid).orElseThrow(() -> new NotFoundException(NOT_FOUND_BY_ID, userUuid));
        log.info("Found user with UUID {}", user.getUuid());
        return userMapper.entityToDto(user);
    }


    public void passwordValidation(UserRequestDto requestDto) {
        if (!requestDto.password().equals(requestDto.repeatedPassword())) {
            throw new PasswordConflictException(PASSWORD_CONFLICT);
        }
    }

    public void existByMail(UserRequestDto requestDto) {
        if (repository.existsByEmail(requestDto.email())) {
            throw new AlreadyExistException(EMAIL_ALREADY_TAKEN, requestDto.email());
        }
    }

    static final class ErrorMessages {
        static final String NOT_FOUND_BY_ID = "User with id %d not found";
        static final String EMAIL_ALREADY_TAKEN = "This email %s is already taken";
        static final String PASSWORD_CONFLICT = "The passwords given aren't the same ";

    }

}


