package com.example.catalogo.domains.entities.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.catalogo.domains.entities.Actor;
import com.example.catalogo.domains.entities.Film;
import com.example.catalogo.domains.entities.FilmActor;
import com.example.catalogo.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

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
            BigDecimal replacementCost, String description, List<FilmActor> actors) {
        this.filmId = filmId;
        this.title = title;
        this.language = language;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
        this.description = description;
        this.actors = actors.stream().map(filmActor -> filmActor.getActor().getActorId()).collect(Collectors.toList());
    }

    public FilmPostDTO() {
    }

    @JsonProperty("id")
    private int filmId;

    // @NotNull
    @JsonProperty("periodo")
    private Byte rentalDuration;

    @JsonProperty("sancion")

    private BigDecimal rentalRate;

    @JsonProperty("coste")

    private BigDecimal replacementCost;
    // @Schema(description = "El título de la película")
    // @NotBlank
    @Size(min = 2, max = 128)
    @JsonProperty("titulo")
    private String title;
    // @Schema(description = "El identificador del idioma de la película")
    @NotNull
    @JsonProperty("idioma")
    private Language language;

    @JsonProperty("descripcion")
    @Size(min = 2, max = 128)
    private String description;

    @JsonProperty("actores")
    private List<Integer> actors;

    // @JsonProperty("categorias")
    // private List<Integer> categories;

    // @Schema(description = "El identificador del idioma original de la película")

    public static FilmPostDTO from(Film film) {
        return new FilmPostDTO(
                film.getFilmId(),
                film.getTitle(),
                film.getLanguage(),
                film.getRentalDuration(),
                film.getRentalRate(),
                film.getReplacementCost(),
                film.getDescription(),
                film.getActors());
    }

    public static Film from(FilmPostDTO source) {
        Film rslt = new Film(
                source.getFilmId(),
                source.getTitle(),
                source.getLanguage(),
                source.getRentalDuration(),
                source.getRentalRate(),
                source.getReplacementCost(),
                source.getActors(),
                source.getDescription()

        );
        return rslt;
    }

    public FilmPostDTO(int filmId2, String title2, Language language2, byte rentalDuration2, BigDecimal rentalRate2,
            BigDecimal replacementCost2, String description2, List<Actor> actors2) {
        // TODO Auto-generated constructor stub
    }

}
//