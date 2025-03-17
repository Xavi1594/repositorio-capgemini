package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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

}
