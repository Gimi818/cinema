package com.cinema.user;

import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final ConfirmUser confirmUser;

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String token) {
        confirmUser.confirmUserAccount(token);
        return ResponseEntity.status(HttpStatus.OK).body("Your account has been confirmed.");
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(service.registration(userRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userLoginDto) {

        if (service.authenticate(userLoginDto.email(), userLoginDto.password())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }


}
