package com.cinema.emailSender;

import jakarta.mail.MessagingException;

public interface ConfirmationEmailFacade {
     void sendConfirmationEmail(String to, String confirmationLink)throws MessagingException;
}
