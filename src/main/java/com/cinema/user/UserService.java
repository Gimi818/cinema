package com.cinema.user;

import com.cinema.user.UserMapper.UserMapper;
import com.cinema.user.dto.UserRequestDto;
import com.cinema.user.exception.EmailAlreadyExistsException;
import com.cinema.user.exception.NotSamePasswordException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {


    private final UserRepository repository;
    private final UserMapper mapper;


    public User createUser(UserRequestDto requestDto) {
        if (repository.existsByEmail(requestDto.email())) {
            throw new EmailAlreadyExistsException(requestDto.email());
        }
        if (!requestDto.password().equals(requestDto.repeatedPassword())) {
            throw new NotSamePasswordException();
        }
        log.info("Saving user {}", requestDto.email());
        User user = repository.save(mapper.dtoToEntity(requestDto));
        log.info("Saved user");
        return user;
    }


}
