package com.example.catalogo.domains.entities.models;

import java.math.BigDecimal;
import java.util.List;

import com.example.catalogo.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Value;

//@Schema(name = "Pelicula (Detalles)", description = "Version completa de las peliculas")
@Value
public class FilmDetailsDTO {
    // @Schema(description = "Identificador de la pelicula", accessMode =
    // AccessMode.READ_ONLY)
    @JsonProperty("id")
    private int filmId;
    // @Schema(description = "Una breve descripción o resumen de la trama de la
    // película")
    @JsonProperty("descripcion")
    private String description;
    // @Schema(description = "La duración de la película, en minutos")
    private Integer length;
    // @Schema(description = "La clasificación por edades asignada a la película",
    // allowableValues = {"G", "PG", "PG-13", "R", "NC-17"})
    private String rating;
    @JsonProperty("valoracion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    // @Schema(description = "El año en que se estrenó la película")
    private Short releaseYear;
    @JsonProperty("lanzamiento")
    // @Schema(description = "La duración del período de alquiler, en días")
    private Byte rentalDuration;
    // @Schema(description = "El coste de alquilar la película por el período
    // establecido")
    private BigDecimal rentalRate;
    // @Schema(description = "El importe cobrado al cliente si la película no se
    // devuelve o se devuelve en un estado dañado")
    private BigDecimal replacementCost;
    // @Schema(description = "El título de la película")
    @JsonProperty("titulo")
    private String title;
    // @Schema(description = "El idioma de la película")
    @JsonProperty("idioma")
    private String language;
    // @Schema(description = "El idioma original de la película")
    @JsonProperty("idiomaVo")
    private String languageVO;
    // @Schema(description = "Contenido Adicional")
    private List<String> specialFeatures;
    // @Schema(description = "La lista de actores que participan en la película")
    @JsonProperty("actores")
    private List<String> actors;
    // @Schema(description = "La lista de categorías asignadas a la película")
    @JsonProperty("categorias")
    private List<String> categories;

    public static FilmDetailsDTO from(Film source) {
        return new FilmDetailsDTO(
                source.getFilmId(),
                source.getDescription(),
                source.getLength(),
                source.getRating() == null ? null : source.getRating().getValue(),
                source.getReleaseYear(),
                source.getRentalDuration(),
                source.getRentalRate(),
                source.getReplacementCost(),
                source.getTitle(),
                source.getLanguage() == null ? null : source.getLanguage().getName(),
                source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
                source.getSpecialFeatures().stream().map(item -> item.getValue()).sorted().toList(),
                source.getActors().stream().map(item -> item.getFirstName() + " " + item.getLastName())
                        .sorted().toList(),
                source.getCategories().stream().map(item -> item.getName()).sorted().toList());
    }
}