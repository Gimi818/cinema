package com.cinema.screening;

import com.cinema.screening.dto.ScreeningRequestDto;
import com.cinema.screening.dto.ScreeningResponseDto;
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

    public Screening saveScreening(ScreeningRequestDto screeningRequestDto) {
        log.info("Saving Screening {}", screeningRequestDto);
        Screening screening = repository.save(mapper.dtoToEntity(screeningRequestDto));
        log.info("Saved Screening {}", screening);
        return screening;
    }

    public List<ScreeningResponseDto> findAllScreening() {
        return repository.findAll().stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }


    public List<ScreeningResponseDto> getScreeningsByDate(LocalDate date) {
        List<Screening> screenings = repository.findScreeningsByDate(date);
        return screenings.stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

}
