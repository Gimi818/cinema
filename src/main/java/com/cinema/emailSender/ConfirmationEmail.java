package com.cinema.emailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationEmail {
    private final JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String to, String confirmationLink) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Potwierdzenie rejestracji");
        String text = "Dziękujemy za rejestrację na naszej stronie!<br/> " +
                "Aby dokończyć rejstrację i mieć możliwość rezerwacji biletu: <br/>" +
                " Kliknij poniższy link, aby potwierdzić swój e-mail:<br/><a href='" + confirmationLink + "'>" + confirmationLink + "</a>";

        helper.setText(text, true);

        javaMailSender.send(message);
    }
}
