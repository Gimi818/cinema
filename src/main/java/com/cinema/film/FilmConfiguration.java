package com.cinema.film;

import com.cinema.screening.ScreeningFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
class FilmConfiguration {


    @Bean
    public FilmFacade filmFacade(@Lazy final FilmService filmService) {
        return filmService;
    }


}
