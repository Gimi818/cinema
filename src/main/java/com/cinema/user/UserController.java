package com.cinema.user;

import com.cinema.film.Film;
import com.cinema.film.dto.FilmRequestDto;
import com.cinema.user.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @PostMapping("/registration")
    public ResponseEntity<User> createUser(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(service.createUser(userRequestDto), HttpStatus.CREATED);
    }


}
