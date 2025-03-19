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
        String title) {
    public static FilmShortDTO from(Film source) {
        return new FilmShortDTO(source.getFilmId(), source.getTitle());
    }
}