package com.cinema.film.exception;

public class FilmExistByTitleException  extends  RuntimeException{
    public FilmExistByTitleException(String title){
        super(String.format("The film of this name '%s' is exists ",title));
    }
}
