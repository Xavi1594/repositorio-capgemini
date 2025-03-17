package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LanguageTest {

    @Test
    void testGetLanguageId() {
        Language language = new Language(1, "English");
        assertEquals(1, language.getLanguageId());
    }

}
