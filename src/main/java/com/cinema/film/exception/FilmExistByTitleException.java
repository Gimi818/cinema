package com.cinema.film.exception;

public class FilmExistByTitleException  extends  RuntimeException{
    public FilmExistByTitleException(String title){
        super(String.format("Wrong film category '%s' ",title));
    }
}
