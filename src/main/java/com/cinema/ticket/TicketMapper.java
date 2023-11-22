package com.cinema.ticket;

import com.cinema.ticket.dto.TicketBookedDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TicketMapper {

    TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

    TicketBookedDto bookedTicketToDto(Ticket ticket);
}
