package com.example.catalogo.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("getCategoryId")
    void testGetCategoryId() {
        Category category = new Category();
        category.setCategoryId(1);
    }

    @Test
    @DisplayName("test validate  name")
    void testNameValidation() {
        Category category = new Category();
        category.setName("Lamine");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre con menos de 2 caracteres no debería ser válido");

        category.setName("NombreMuyLargoQueExcedeElLimiteDeCuarentaYCincoCaracteres");
        violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre con más de 45 caracteres no debería ser válido");
    }

    @Test
    void testCreateCategory() {
        Category category = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        assertNotNull(category);
    }

    @Test
    void testEquals() {
        Category category = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        Category category2 = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        assertEquals(category, category2);
    }

    @Test
    void testNotEquals() {
        Category category = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        Category category2 = new Category(2, "TERROR", new ArrayList<FilmCategory>());
        assertNotEquals(category, category2);
    }

    @Test
    void testHashCode() {
        Category category = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        Category category2 = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        assertEquals(category.hashCode(), category2.hashCode());
    }

    @Test
    void testNameNull() {
        Category category = new Category();
        category.setName(null);
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre no debería ser nulo");
    }

    @Test
    void testCategoryWithEmptyName() {
        Category category = new Category(1, "", new ArrayList<FilmCategory>());
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertFalse(violations.isEmpty(), "El nombre no debería estar vacío");
    }

    @Test
    void testNameContainsÑ() {
        Category category = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        category.setName("TERRORÑ");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertTrue(violations.isEmpty(), "El nombre puede contener ñ");
    }

    @Test
    void testDeleteFilmCategory() {
        Category category = new Category(1, "TERROR", new ArrayList<FilmCategory>());
        FilmCategory filmCategory = new FilmCategory();
        category.addFilmCategory(filmCategory);
        category.removeFilmCategory(filmCategory);
        assertTrue(category.getFilmCategories().isEmpty());
    }

}