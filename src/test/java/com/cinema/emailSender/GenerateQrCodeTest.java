package com.cinema.emailSender;

import com.cinema.ticket.Ticket;
import com.cinema.ticket.ticketEnum.Currency;
import com.cinema.ticket.ticketEnum.TicketStatus;
import com.cinema.ticket.ticketEnum.TicketType;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class GenerateQrCodeTest {


    @Mock
    private Ticket ticket;


    @InjectMocks
    private GenerateQrCode generateQrCode;

//    @Test
//    @DisplayName("Should create Qr code")
//    void Should_create_Qr_code() throws BadElementException, IOException {
//        String email = "test@example.com";
//        Ticket newTicket = new Ticket(1L, "Adam", "New", LocalDate.of(2023, 11, 11),
//                LocalTime.of(12, 12), BigDecimal.valueOf(11), 1, 1, 1, 1L,
//                TicketStatus.ACTIVE, TicketType.NORMAL, Currency.USD);
//
//        Element qrElement = generateQrCode.createQr(email, newTicket);
//
//        assertNotNull(qrElement);
//
//
//    }
}


