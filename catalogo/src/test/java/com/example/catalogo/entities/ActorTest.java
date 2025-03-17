package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
