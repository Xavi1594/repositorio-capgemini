package com.example.catalogo.aplication.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogo.domains.contracts.services.CategoriesService;
import com.example.catalogo.domains.entities.Category;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/categories/v1")
@Tag(name = "categories", description = "Description API")
public class CategoryController {

    private final CategoriesService categoriesService;

    public CategoryController(CategoriesService categoriesService) {
        super();
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoriesService.getAll();
    }

}
