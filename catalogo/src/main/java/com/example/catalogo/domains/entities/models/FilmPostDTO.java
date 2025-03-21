package com.example.catalogo.domains.entities.models;

import java.math.BigDecimal;

import com.example.catalogo.domains.entities.Film;
import com.example.catalogo.domains.entities.Language;

//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Schema(name = "Pelicula (Editar)", description = "Version editable de las películas")
@Data
@AllArgsConstructor
public class FilmPostDTO {

    public FilmPostDTO(int filmId, String title, Language language, Byte rentalDuration, BigDecimal rentalRate,
            BigDecimal replacementCost) {
        this.filmId = filmId;
        this.title = title;
        this.language = language;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
    }

    public FilmPostDTO() {
    }

    private int filmId;
    // @Schema(description = "Una breve descripción o resumen de la trama de la
    // película", minLength = 2)

    @NotNull
    private Byte rentalDuration;
    // @Schema(description = "El coste de alquilar la película por el período
    // establecido", minimum = "0", exclusiveMinimum = true)
    @NotNull
    private BigDecimal rentalRate;
    // @Schema(description = "El importe cobrado al cliente si la película no se
    // devuelve o se devuelve en un estado dañado", minimum = "0", exclusiveMinimum
    // = true)
    @NotNull
    private BigDecimal replacementCost;
    // @Schema(description = "El título de la película")
    @NotBlank
    @Size(min = 2, max = 128)
    private String title;
    // @Schema(description = "El identificador del idioma de la película")
    @NotNull
    private Language language;
    // @Schema(description = "El identificador del idioma original de la película")

    public static FilmPostDTO from(Film film) {
        return new FilmPostDTO(
                film.getFilmId(),
                film.getTitle(),
                film.getLanguage(), // Asumiendo que 'Language' tiene un método 'getLanguageId'
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getReplacementCost());
    }

    public static Film from(FilmPostDTO source) {
        Film rslt = new Film(
                source.getFilmId(),
                source.getTitle(),
                source.getLanguage(),
                source.getRentalDuration(),
                source.getRentalRate(),
                source.getReplacementCost()

        );
        return rslt;
    }

}
//