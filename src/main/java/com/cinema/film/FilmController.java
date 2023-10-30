package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.filmCategory.FilmCategory;
import com.cinema.screening.dto.ScreeningResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService service;

    @PostMapping("/add")
    public ResponseEntity<Film> saveFilm(@RequestBody FilmRequestDto filmRequestDto) {
        return new ResponseEntity<>(service.saveFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<FilmResponseDto>> findAll() {
        List<FilmResponseDto> allFilms = service.findAllFilms();
        return ResponseEntity.status(HttpStatus.OK).body(allFilms);
    }

    @GetMapping("/category")
    public ResponseEntity<List<FilmResponseDto>> getFilmsByCategory(@RequestParam("category") FilmCategory filmCategory) {
        List<FilmResponseDto> films = service.findFilmByCategory(filmCategory);
        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        service.deleteFilm(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
