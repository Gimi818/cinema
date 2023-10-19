package com.cinema.screening;

import com.cinema.screening.dto.ScreeningRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository repository;
    private final ScreeningMapper mapper;

    public Screening saveScreening(ScreeningRequestDto screeningRequestDto) {
        Screening newScreening = repository.save(mapper.dtoToEntity(screeningRequestDto));
        return newScreening;
    }

    public List<Screening> getScreeningsByDate(LocalDateTime dateTime) {
        Screening screeningg = repository.getScreeningsByDate(dateTime);
        return repository.getScreeningsByDate(dateTime.getDate());
    }


}
