package com.example.catalogo.domains.entities.models;

import com.example.catalogo.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

//@Schema(name = "Pelicula (Corto)", description = "Version corta de las peliculas")
public record FilmShortDTO(
        @JsonProperty("id")
        // @Schema(description = "Identificador de la pelicula", accessMode =
        // AccessMode.READ_ONLY)
        int filmId,
        @JsonProperty("titulo")
        // @Schema(description = "Titulo de la pelicula")
        String title,
        @JsonProperty("Descripción") String description) {
    public static FilmShortDTO from(Film film) {
        return new FilmShortDTO(film.getFilmId(), film.getTitle(), film.getDescription());
    }

    public static Film from(FilmShortDTO source) {
        return new Film(
                source.filmId(),
                source.title(),
                source.description());
    }
}