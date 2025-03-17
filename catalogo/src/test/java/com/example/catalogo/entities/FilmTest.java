package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class FilmTest {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Is a valid film")
    void isValidTest() {
        var fixture = new Film();
        fixture.setTitle("The Lord of the Rings");
        fixture.setLength(120);
        fixture.setRating("G");
        fixture.setRentalDuration((byte) 5);
        fixture.setRentalRate(null);
        assertTrue(fixture.isValid());

    }

    @Test
    void testTitleValidation() {
        Film film = new Film();
        film.setTitle(""); // Título vacío

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "El título no debería estar vacío");

        film.setTitle("A".repeat(129));
        violations = validator.validate(film);
        assertFalse(violations.isEmpty(), "El título no debería exceder los 128 caracteres");
    }

    @Test
    void testFilmWithTitleNull() {
        Film film = new Film();
        film.setTitle(null);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
    }

    @Test
    void testFilmWithTooLongTitle() {
        Film film = new Film();
        film.setTitle(
                "\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.");

        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        assertFalse(violations.isEmpty(), "Debe haber errores de validación");

        assertEquals(1, violations.size());
        assertEquals("El título no debería exceder los 128 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void testFilmAddRemoveFilmActor() {
        Film film = new Film();
        film.setTitle("Maldítos bastardos");

        film.setFilmActors(new ArrayList<>());

        FilmActor actor = new FilmActor();
        film.addFilmActor(actor);

        assertEquals(1, film.getFilmActors().size(), "El actor tiene que ser añadido a la lista de actores.");
        assertEquals(film, actor.getFilm(), "Se debe asociar un actor a la película");

        film.removeFilmActor(actor);

        assertEquals(0, film.getFilmActors().size(), "El actor debe ser eliminado de la lista.");
        assertNull(actor.getFilm(), "El actor debe ser quitado de la película.");
    }
}
