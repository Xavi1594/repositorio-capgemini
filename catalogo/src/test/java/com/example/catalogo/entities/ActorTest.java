package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.sql.Timestamp;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.catalogo.domains.entities.Actor;
import com.example.catalogo.domains.entities.FilmActor;

public class ActorTest {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    Actor actor = new Actor(1, "Lamine", "Yamal");

    @Test
    @DisplayName("Is a valid actor")
    void isValidTest() {
        var fixture = new Actor(0, "PEPITO", "GRILLO");
        assertTrue(fixture.isValid());
    }

    @Test
    void testGetActorId() {
        assertEquals(1, actor.getActorId());

    }

    @Test
    void testGetFirstName() {
        assertEquals("Lamine", actor.getFirstName());
    }

    @Test
    void testLastNameValidation() {
        Actor actor = new Actor(1, "Lamine", "Y");
        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty(), "El apellido con menos de 2 caracteres no debería ser válido");

        actor.setLastName("ApellidoMuyLargoQueExcedeElLimiteDeCuarentaYCincoCaracteres");
        violations = validator.validate(actor);
        assertFalse(violations.isEmpty(), "El apellido con más de 45 caracteres no debería ser válido");

        actor.setLastName("");
        violations = validator.validate(actor);
        assertFalse(violations.isEmpty(), "El apellido no debería estar vacío");
    }

    @Test
    void createActor() {
        assertEquals(1, actor.getActorId());
        assertEquals("Lamine", actor.getFirstName());
        assertEquals("Yamal", actor.getLastName());
    }

    @Test
    void testHashCode() {
        Actor actor2 = new Actor(1, "Lamine", "Yamal");
        assertEquals(actor.hashCode(), actor2.hashCode());
    }

    @Test
    void testActorWithEmptyFirstName() {
        Actor actor = new Actor(1, "", "Yamal"); // Nombre vacío

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testActorWithInvalidFirstName() {
        Actor actor1 = new Actor(1, null, "Yamal");
        assertNotEquals("Lamine", actor1.getFirstName());
    }

    @Test
    void testActorWithBlankFirstName() {
        Actor actor = new Actor(1, " ", "Yamal");
        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testEquals() {
        Actor actor2 = new Actor(1, "Lamine", "Yamal");
        assertEquals(actor, actor2);

    }

    @Test
    @DisplayName("First name can contain ñ")
    void testFirstNameWithÑ() {
        Actor actor = new Actor(1, "PEPEÑITO", "GRILLO");

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertTrue(violations.isEmpty(), "El nombre con ñ debería ser válido");
    }

    @Test
    void testLastUpdateValidation() {
        Actor actor = new Actor(1, "Lamine", "Yamal");
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis() + 10000));

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty(), "La fecha de actualización no debería ser futura");
    }

    @Test
    void testAddFilmActor() {

        FilmActor filmActor = new FilmActor();

        actor.setFilmActors(new ArrayList<>());

        FilmActor addedFilmActor = actor.addFilmActor(filmActor);

        assertNotNull(actor.getFilmActors());
        assertEquals(1, actor.getFilmActors().size());
        assertTrue(actor.getFilmActors().contains(filmActor));
        assertSame(filmActor, addedFilmActor);
        assertEquals(actor, filmActor.getActor());
    }

}
