package com.cinema.film;

import com.cinema.film.filmCategory.FilmCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
 interface FilmRepository extends JpaRepository<Film,Long> {


    @Query("SELECT f FROM Film f WHERE f.category = :category")
    List<Film> findByCategory(@Param("category") FilmCategory category);

    boolean existsByTitle(String title);
}

