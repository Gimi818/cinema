package com.cinema.screening;

import com.cinema.screening.dto.ScreeningAvailableSeats;
import com.cinema.screening.exception.ScreeningNotFoundByIdException;
import com.cinema.seats.Seat;
import com.cinema.seats.SeatService;
import com.cinema.seats.SeatStatus;
import com.cinema.film.Film;
import com.cinema.film.FilmRepository;
import com.cinema.film.exception.FilmNotFoundException;
import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
@Log4j2
public class ScreeningService {

    private final ScreeningRepository repository;
    private final ScreeningMapper mapper;
    private final FilmRepository filmRepository;
    private final ScreeningValidate validate;
    private final SeatService seatService;

    @Transactional
    public Screening saveScreening(ScreeningRequestDto screeningRequestDto, Long filmId) {
        log.info("Saving Screening {}", screeningRequestDto);

        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(filmId));

        validate.dataValidation(screeningRequestDto,film);

        Screening screening = repository.save(mapper.dtoToEntity(screeningRequestDto));
        screening.setFilm(film);
        screening.setSeats(createSeats());
        log.info("Saved Screening ");
        return screening;
    }


    private List<Seat> createSeats() {
        log.info("Created seats");
        return IntStream.rangeClosed(1, 10)
                .boxed()
                .flatMap(rowNumber -> IntStream.rangeClosed(1, 10)
                        .mapToObj(seatInRow -> seatService.createSeat(rowNumber, seatInRow, SeatStatus.AVAILABLE)))
                .collect(Collectors.toList());

    }

    public List<ScreeningResponseDto> findAllScreenings() {
        log.info("Returning all screenings");
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScreeningResponseDto> getScreeningsByDate(LocalDate date) {
        validate.checkCorrectData(date);
        List<Screening> screenings = repository.findScreeningsByDate(date);
        log.info("Found {} screenings.", screenings.size());
        log.info("Returning all screenings by date {}", date);
        return screenings.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    public ScreeningAvailableSeats findAvailableSeats(Long id) {
        Screening screening = repository.findById(id).orElseThrow(() -> new ScreeningNotFoundByIdException(id));
        log.info("Returning seats by screening id ->  {} id",id);
        return mapper.screeningToSeatsDto(screening);
    }

}




