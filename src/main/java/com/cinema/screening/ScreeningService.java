package com.cinema.screening;

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


@Service
@AllArgsConstructor
@Log4j2
public class ScreeningService {

    private final ScreeningRepository repository;
    private final ScreeningMapper mapper;
    private final FilmRepository filmRepository;
    private final ScreeningValidate validate;

    @Transactional
    public Screening saveScreening(ScreeningRequestDto screeningRequestDto, Long filmId) {
        log.info("Saving Screening {}", screeningRequestDto);
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(filmId));
        validate.dataValidation(screeningRequestDto);
        Screening screening = repository.save(mapper.dtoToEntity(screeningRequestDto));
        log.info("Saved Screening ");
        return screening;
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

}
