package com.cinema.ticket;

import com.cinema.ticket.dto.TicketBookedDto;
import com.cinema.ticket.dto.TicketBookingDto;

import com.cinema.ticket.ticketEnum.Currency;

import com.cinema.ticket.ticketEnum.TicketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TicketControllerTest {
    @MockBean
    private TicketService service;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static TicketBookedDto ticketBookedDto;

    private static Ticket ticket;
    private static TicketBookingDto ticketBookingDto;

    private static String ticketRequestDtoJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        ticketBookingDto = new TicketBookingDto(TicketType.NORMAL, Currency.PLN, 1, 1);
        ticketRequestDtoJson = objectMapper.writeValueAsString(ticketBookingDto);
    }

    @Test
    @DisplayName("Should book a ticket")
    void should_book_ticket() throws Exception {
        given(service.bookTicket(1L, 1L, ticketBookingDto)).willReturn(ticketBookedDto);

        mockMvc.perform(post("/book/1/1")
                        .content(ticketRequestDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
