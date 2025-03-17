package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LanguageTest {

    @Test
    void testGetLanguageId() {
        Language language = new Language(1, "English");
        assertEquals(1, language.getLanguageId());
    }

    @Test
    void testSetLanguageId() {
        Language language = new Language();
        language.setLanguageId(1);
        assertEquals(1, language.getLanguageId());
    }

    @Test
    void testGetName() {
        Language language = new Language(1, "English");
        assertEquals("English", language.getName());
    }

    @Test
    void testNullName() {
        Language language = new Language(1, null);
        assertEquals(null, language.getName());
    }

    @Test
    void TestCreateLanguage() {
        Language language = new Language(1, "English");
        assertEquals(1, language.getLanguageId());
        assertEquals("English", language.getName());
    }

    @Test
    void TestDeleteLanguage() {
        Language language = new Language(1, "English");
        language = null;
        assertEquals(null, language);
    }

    @Test
    void TestNameContainsÑ() {
        Language language = new Language(1, "Español");
        assertEquals("Español", language.getName());
    }

}
