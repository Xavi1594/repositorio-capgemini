package com.example.catalogo.aplication.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.domains.contracts.repositories.LanguageRepository;
import com.example.catalogo.domains.contracts.services.LanguageService;
import com.example.catalogo.domains.entities.Language;
import com.example.catalogo.exceptions.BadRequestException;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/languages/v1")
@Tag(name = "Languages", description = "Languages API")
public class LanguageController {
    private final LanguageRepository languageRepository;
    private LanguageService languageService;

    public LanguageController(LanguageRepository languageRepository, LanguageService languageService) {
        this.languageService = languageService;
        this.languageRepository = languageRepository;
    }

    @GetMapping
    @JsonView(Language.Partial.class)
    public List<Language> getAllLanguages() throws Exception {
        return languageRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    @JsonView(Language.Complete.class)
    public Language getOne(@PathVariable int id) throws NotFoundException {
        var item = languageRepository.findById(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el idioma con id " + id);
        }
        return item.get();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiResponse(responseCode = "201", description = "Language created")
    @Operation(description = "Create a new language")
    @JsonView(Language.Partial.class)
    public ResponseEntity<Language> create(@Valid @RequestBody Language item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = languageService.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getLanguageId())
                .toUri();
        return ResponseEntity.created(location).body(newItem);
    }

    @PutMapping(path = "/{id}")
    @JsonView(Language.Partial.class)
    public Language modify(@PathVariable int id, @Valid @RequestBody Language item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getLanguageId() != id)
            System.out.println("ID recibido: " + id);
        System.out.println("ID del lenguaje: " + item.getLanguageId());

        if (languageService.getOne(item.getLanguageId()).isEmpty())
            throw new NotFoundException();
        languageService.modify(item);
        return item;
    }

    @DeleteMapping(path = "/{id}")
    @ApiResponse(responseCode = "204", description = "Language deleted")
    @Operation(description = "Delete a language")
    public void delete(@PathVariable int id) throws NotFoundException {
        if (languageService.getOne(id).isEmpty())
            throw new NotFoundException();
        languageService.deleteById(id);
    }
}
