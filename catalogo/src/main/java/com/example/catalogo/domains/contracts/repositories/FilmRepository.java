package com.example.catalogo.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.catalogo.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film> {
    @Query(value = "SELECT f FROM Film f WHERE f.filmId > ?1")
    List<Film> findByFilmIdGreaterThan(int id);

    List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp lastUpdate);

    <T> List<T> findAllBy(Class<T> type);

    <T> Iterable<T> findAllBy(Sort sort, Class<T> type);

    <T> Page<T> findAll(Pageable pageable, Class<T> type);

}
