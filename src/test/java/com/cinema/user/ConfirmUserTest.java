package com.cinema.user;


import com.cinema.emailSender.ConfirmationEmailFacade;
import com.cinema.user.userEnum.AccountType;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest
class ConfirmUserTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfirmationEmailFacade confirmationEmail;
    @InjectMocks
    private ConfirmUser confirmUser;


    @Test
    @DisplayName("Should generate token")
    public void should_generate_token() {
        String token = confirmUser.generateConfirmationToken();
        assertEquals(36, token.length());
    }



    @Test
    @DisplayName("Should generate confirmation email")
    public void should_generate_confirmation_email() {

        User user = new User();
        user.setConfirmationToken("test_token");
        String confirmationLink = confirmUser.generateConfirmationLink(user, "http://localhost:8080/users/confirm?token=");
        assertEquals("http://localhost:8080/users/confirm?token=test_token", confirmationLink);
    }


//    @Value("${confirmation.link}")
//    public String confirmationLink;
//    @Test
//    @DisplayName("Should send confirmation email")
//    public void should_send_email() throws MessagingException {
//        User user = new User();
//        user.setEmail("test@example.com");
//        user.setConfirmationToken("test_token");
//
//        doNothing().when(confirmationEmail).sendConfirmationEmail(user.getEmail(),
//                confirmUser.generateConfirmationLink(user,confirmationLink));
//        confirmUser.sendConfirmationEmail(user);
//
//        verify(confirmationEmail, times(1))
//                .sendConfirmationEmail("test@example.com", "http://localhost:8080/users/confirm?token=test_token");
//    }


    @Test
    @DisplayName("Should confirm account")
    public void should_confirm_account() {
        User user = new User();
        user.setConfirmationToken("test_token");

        when(userRepository.findByConfirmationToken("test_token")).thenReturn(Optional.of(user));

        confirmUser.confirmUserAccount("test_token");

        assertEquals(AccountType.ACTIVE, user.getAccountType());
        assertEquals(null, user.getConfirmationToken());

        verify(userRepository, times(1)).save(user);
    }


}
