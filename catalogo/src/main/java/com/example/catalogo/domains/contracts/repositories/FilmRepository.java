package com.example.catalogo.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.catalogo.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {
    @Query(value = "SELECT f FROM Film f WHERE f.filmId > ?1")
    List<Film> findByFilmIdGreaterThan(int id);

}
