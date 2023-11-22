package com.cinema.user;

import com.cinema.common.exception.exceptions.AlreadyExistException;
import com.cinema.common.exception.exceptions.PasswordConflictException;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import com.cinema.user.encoder.PasswordEncoderService;
import com.cinema.user.userEnum.AccountType;
import com.cinema.user.userEnum.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.when;


@SpringBootTest

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRequestDto userRequestDto;
    @Mock
    private UserResponseDto userResponseDto;
    @Mock
    private ConfirmUser confirmUser;
    @Mock
    private PasswordEncoderService passwordEncoderService;

    @Mock
    private User user;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto("Adam","Buu","ab@o.com","qwerty","qwerty", UserRole.ADMIN);
        user = new User("Adam","Buu","ab@o.com","qwerty",UserRole.ADMIN, AccountType.UNCONFIRMED,"janskdjnjnj");
        userResponseDto = new UserResponseDto("A","B","ab@o.com",UserRole.ADMIN);
    }

    @Test
    @DisplayName("Should save user")
    void should_save_user() {

        given(userRepository.save(userMapper.dtoToEntity(userRequestDto)))
                .willReturn(user);

        assertThat(userService.registration(userRequestDto))
                .isEqualTo(userMapper.createdEntityToDto(user));
    }
    @Test
    @DisplayName("Should find user by id")
    void should_find_user_by_id() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userMapper.entityToDto(user))
                .willReturn(userResponseDto);

            assertThat(userService.findUserById(1L)).isEqualTo(userResponseDto);
    }

    @Test
    @DisplayName("Should throw NotSamePasswordException")
    void Should_throw_NotSamePasswordException() {
        // Given
        UserRequestDto requestDto = new UserRequestDto("Max", "Ferdo", "john@example.com", "password1", "password2", UserRole.USER);

        // When & Then
        assertThrows(PasswordConflictException.class, () -> userService.passwordValidation(requestDto));
    }


    @Test
    @DisplayName("Should throw EmailAlreadyExistsException")
    void Should_throw_EmailAlreadyExistsException() {
        // Given
        UserRequestDto requestDto = new UserRequestDto("Max", "Doe", "john@example.com", "password", "password", UserRole.USER);
        when(userRepository.existsByEmail(requestDto.email())).thenReturn(true);

        // When & Then
        assertThrows(AlreadyExistException.class, () -> userService.existByMail(requestDto));
    }



}




