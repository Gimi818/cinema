package com.cinema.film;

import com.cinema.film.dto.FilmRequestDto;
import com.cinema.film.dto.FilmResponseDto;
import com.cinema.film.filmCategory.FilmCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FilmControllerTest {
    @MockBean
    private FilmService filmService;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private static FilmRequestDto filmRequestDto;
    private static String filmRequestDtoJson;
    private static Film film;
    private static FilmResponseDto filmResponseDto;
    private static FilmResponseDto secondFilmResponseDto;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        filmRequestDto = new FilmRequestDto("TOP GUN", FilmCategory.ACTION, 120);
        filmRequestDtoJson = objectMapper.writeValueAsString(filmRequestDto);
        filmResponseDto = new FilmResponseDto(1L, "TOP GUN", FilmCategory.ACTION, 120);
        secondFilmResponseDto = new FilmResponseDto(2L, "AS", FilmCategory.ACTION, 110);


    }

    @Test
    @DisplayName("Should find film by id")
    void should_find_film_by_id() throws Exception {
        given(filmService.findFilmById(1L)).willReturn(filmResponseDto);

        mockMvc.perform(get("/films/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("TOP GUN"))
                .andExpect(jsonPath("$.category").value("ACTION"))
                .andExpect(jsonPath("$.durationFilmInMinutes").value(120));
    }

    @Test
    @DisplayName("Should save film")
    void save_film() throws Exception {
        given(filmService.saveFilm(filmRequestDto)).willReturn(film);

        mockMvc.perform(post("/films/add")
                        .content(filmRequestDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Should find all films")
    void should_find_all_films() throws Exception {
        List<FilmResponseDto> filmResponseList = List.of(filmResponseDto, secondFilmResponseDto);
        given(filmService.findAllFilms())
                .willReturn(filmResponseList);

        MvcResult mvcResult = mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andReturn();

        String filmDtoJson = mvcResult.getResponse().getContentAsString();
        List<Film> filmsResult = new ObjectMapper()
                .readValue(filmDtoJson, new TypeReference<List<Film>>() {
                });
        assertThat(filmsResult).hasSize(2);
    }

    @Test
    @DisplayName("Should find all films by category")
    void should_find_films_by_category() throws Exception {
        // Given
        List<FilmResponseDto> filmResponseList = List.of(filmResponseDto, secondFilmResponseDto);
        FilmCategory category = FilmCategory.ACTION;
        given(filmService.findFilmByCategory(category))
                .willReturn(filmResponseList);

        // When
        MvcResult mvcResult = mockMvc.perform(get("/films/category")
                        .param("category", category.toString()))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String filmDtoJson = mvcResult.getResponse().getContentAsString();
        List<Film> filmsResult = new ObjectMapper()
                .readValue(filmDtoJson, new TypeReference<List<Film>>() {
                });
        assertThat(filmsResult).hasSize(2);

    }
    @Test
    @DisplayName("Should delete film by id")
    void should_delete_film() throws Exception {
        mockMvc.perform(delete("/films/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(filmService, Mockito.times(1))
                .deleteFilm(1L);
    }
}
