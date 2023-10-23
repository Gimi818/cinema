package com.cinema.film.exception;

public class FilmCategoryFormatException extends RuntimeException{
    public FilmCategoryFormatException(){
        super(String.format("Film with title is exist "));
    }
}
