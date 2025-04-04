package com.example.catalogo.domains.core.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Value;

public class EntityBaseTest {

    @Value
    class Dummy extends AbstractEntity<Dummy> {
        @Positive
        int id;
        @NotBlank
        @Size(min = 2, max = 10)
        String nombre;
    }

    @Nested
    class OK {
        @Test
        void isValid() {
            var dummy = new Dummy(1, "algo");
            assertAll("valido",
                    () -> assertTrue(dummy.isValid(), "isValid"),
                    () -> assertFalse(dummy.isInvalid(), "isInvalid"),
                    () -> assertEquals(0, dummy.getErrors().size(), "getErrors"),
                    () -> assertNull(dummy.getErrorsFields(), "getErrorsFields"),
                    () -> assertEquals("", dummy.getErrorsMessage(), "getErrorsMessage"));
        }
    }

    @Nested
    class KO {
        @Test
        void isInvalid() {
            var dummy = new Dummy(-1, " ");
            assertAll("invalido",
                    () -> assertFalse(dummy.isValid(), "isValid"),
                    () -> assertTrue(dummy.isInvalid(), "isInvalid"),
                    () -> assertEquals(3, dummy.getErrors().size(), "getErrors"),
                    () -> assertEquals(2, dummy.getErrorsFields().size(), "getErrorsFields"),
                    () -> assertEquals(
                            "ERRORES: id: must be greater than 0. nombre: must not be blank, size must be between 2 and 10.",
                            dummy.getErrorsMessage(), "getErrorsMessage"));
        }

    }

}
