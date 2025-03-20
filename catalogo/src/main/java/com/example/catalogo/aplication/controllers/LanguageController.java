package com.example.catalogo.aplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogo.domains.contracts.repositories.LanguageRepository;
import com.example.catalogo.domains.contracts.services.LanguageService;
import com.example.catalogo.domains.entities.Language;
import com.example.catalogo.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/languages/v1")
@Tag(name = "Languages", description = "Languages API")
public class LanguageController {
    private final LanguageRepository languageRepository;

    public LanguageController(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @GetMapping
    @JsonView(Language.Complete.class)
    public List<Language> getAllLanguages() throws Exception {
        return languageRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @JsonView(Language.Partial.class)
    public Language getOne(@PathVariable int id) throws NotFoundException {
        var item = languageRepository.findById(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el idioma con id " + id);
        }
        return item.get();
    }
}
