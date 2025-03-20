package com.example.catalogo.aplication.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.catalogo.domains.contracts.repositories.LanguageRepository;
import com.example.catalogo.domains.entities.Language;
import com.example.catalogo.exceptions.NotFoundException;

public class LanguageControllerTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageController languageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLanguages() throws Exception {
        Language lang1 = new Language(1, "English");
        Language lang2 = new Language(2, "Spanish");
        List<Language> languages = Arrays.asList(lang1, lang2);

        when(languageRepository.findAll()).thenReturn(languages);

        List<Language> result = languageController.getAllLanguages();

        assertEquals(2, result.size());
        assertEquals("English", result.get(0).getName());
        assertEquals("Spanish", result.get(1).getName());
    }

    @Test
    public void testGetOneLanguageFound() throws Exception {
        Language lang = new Language(1, "English");
        when(languageRepository.findById(1)).thenReturn(Optional.of(lang));

        Language result = languageController.getOne(1);

        assertNotNull(result);
        assertEquals("English", result.getName());
    }

    @Test
    public void testGetOneLanguageNotFound() {
        when(languageRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            languageController.getOne(1);
        });

        String expectedMessage = "No se encontró el idioma con id 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}