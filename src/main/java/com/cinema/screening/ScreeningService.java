package com.cinema.screening;

import com.cinema.screening.dto.ScreeningRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScreeningService {

    private final ScreeningRepository repository;
    private final ScreeningMapper mapper;

    public Screening saveScreening(ScreeningRequestDto screeningRequestDto) {
        Screening newScreening = repository.save(mapper.dtoToEntity(screeningRequestDto));
        return newScreening;
    }


}
