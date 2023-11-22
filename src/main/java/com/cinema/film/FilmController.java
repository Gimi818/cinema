package com.cinema.film;

import com.cinema.film.dto.CreatedFilmDto;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.filmCategory.FilmCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;

    @PostMapping(Routes.SAVE)
    public ResponseEntity<CreatedFilmDto> saveFilm(@RequestBody FilmRequestDto filmRequestDto) {
        return new ResponseEntity<>(service.saveFilm(filmRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(Routes.ROOT)
    public ResponseEntity<List<FilmResponseDto>> findAll() {
        List<FilmResponseDto> allFilms = service.findAllFilms();
        return ResponseEntity.status(HttpStatus.OK).body(allFilms);
    }

    @GetMapping(Routes.FIND_BY_ID)
    public ResponseEntity<FilmResponseDto> findFilmById(@PathVariable Long id) {
        FilmResponseDto filmResponseDto = service.findFilmById(id);
        return ResponseEntity.status(HttpStatus.OK).body(filmResponseDto);
    }

    @GetMapping(Routes.FIND_BY_CATEGORY)
    public ResponseEntity<List<FilmResponseDto>> getFilmsByCategory(@RequestParam("category") FilmCategory filmCategory) {
        List<FilmResponseDto> films = service.findFilmByCategory(filmCategory);
        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @DeleteMapping(Routes.DELETE_BY_ID)
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        service.deleteFilm(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    static final class Routes {
        static final String ROOT = "/films";
        static final String SAVE = "/films";
        static final String DELETE_BY_ID = ROOT + "/{id}";
        static final String FIND_BY_ID = ROOT + "/{id}";
        static final String FIND_BY_CATEGORY = ROOT + "/category";

    }

}
