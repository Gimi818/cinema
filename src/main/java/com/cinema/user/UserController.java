package com.cinema.user;

import com.cinema.user.dto.CreatedUserDto;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cinema.user.UserController.Routes.*;

@RestController
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;
    private final ConfirmUser confirmUser;

    @PostMapping(REGISTRATION)
    public ResponseEntity<CreatedUserDto> registration(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userFacade.registration(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping(CONFIRM)
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String token) {
        confirmUser.confirmUserAccount(token);
        return ResponseEntity.status(HttpStatus.OK).body("Your account has been confirmed.");
    }

    @GetMapping(FIND_USER_BY_ID)
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userFacade.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    static final class Routes {
        static final String ROOT = "/users";
        static final String REGISTRATION = ROOT + "/registration";
        static final String CONFIRM = ROOT + "/confirm";
        static final String FIND_USER_BY_ID = ROOT + "/{id}";

    }

}
