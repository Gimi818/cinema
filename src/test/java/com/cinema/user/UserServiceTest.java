package com.cinema.user;

import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import com.cinema.user.exception.EmailAlreadyExistsException;
import com.cinema.user.exception.NotSamePasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private User user;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto("Adam","Buu","ab@o.com","qwerty","qwerty",UserRole.ADMIN);
        user = new User(1L,"Adam","Buu","ab@o.com","qwerty",UserRole.ADMIN,AccountType.UNCONFIRMED,"janskdjnjnj");
        userResponseDto = new UserResponseDto(1L,"A","B","ab@o.com",UserRole.ADMIN,AccountType.UNCONFIRMED);
    }

    @Test
    @DisplayName("Should save user")
    void should_save_user() {

        given(userRepository.save(userMapper.dtoToEntity(userRequestDto))).willReturn(user);

        User savedUser = userService.registration(userRequestDto);

        assertEquals("Adam",savedUser.getFirstName());
        assertEquals("Buu",savedUser.getLastName());
        assertEquals("ab@o.com",savedUser.getEmail());
        assertEquals(UserRole.ADMIN,savedUser.getRole());
    }
    @Test
    @DisplayName("Should find user by id")
    void should_find_user_by_id() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userMapper.entityToDto(user))
                .willReturn(userResponseDto);

            assertThat(userService.findById(1L)).isEqualTo(userResponseDto);
    }

    @Test
    @DisplayName("Should throw NotSamePasswordException")
    void Should_throw_NotSamePasswordException() {
        // Given
        UserRequestDto requestDto = new UserRequestDto("Max", "Ferdo", "john@example.com", "password1", "password2", UserRole.USER);

        // When & Then
        assertThrows(NotSamePasswordException.class, () -> userService.passwordValidation(requestDto));
    }


    @Test
    @DisplayName("Should throw EmailAlreadyExistsException")
    void Should_throw_EmailAlreadyExistsException() {
        // Given
        UserRequestDto requestDto = new UserRequestDto("Max", "Doe", "john@example.com", "password", "password", UserRole.USER);
        when(userRepository.existsByEmail(requestDto.email())).thenReturn(true);

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> userService.existByMail(requestDto));
    }



}




