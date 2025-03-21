package com.example.catalogo.aplication.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.domains.contracts.services.CategoriesService;
import com.example.catalogo.domains.entities.Category;
import com.example.catalogo.exceptions.BadRequestException;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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

    @GetMapping(path = "/{id}")
    @Operation(description = "Get one entity by id")
    public Category getOne(@PathVariable int id) throws NotFoundException {
        var item = categoriesService.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró la categoria con id " + id);
        }
        return item.get();
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Category created")

    public ResponseEntity<Category> create(@Valid @RequestBody Category item)
            throws BadRequestException, NotFoundException, InvalidDataException, DuplicateKeyException {
        var newItem = categoriesService.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getCategoryId())
                .toUri();
        return ResponseEntity.created(location).body(newItem);

    }

    @PutMapping(path = "/{id}")
    public Category modify(@PathVariable int id, @Valid @RequestBody Category item)
            throws BadRequestException, NotFoundException, InvalidDataException {

        // Verifica que el ID en la URL coincide con el ID del objeto que se está
        // enviando
        if (item.getCategoryId() != id) {
            throw new BadRequestException("El ID del recurso no coincide con el ID proporcionado en el cuerpo.");
        }

        // Verifica que la categoría exista en la base de datos
        if (categoriesService.getOne(id).isEmpty()) {
            throw new NotFoundException("Categoría no encontrada.");
        }

        // Llama al servicio para modificar la categoría
        categoriesService.modify(item);

        // Retorna el objeto modificado
        return item;
    }

}
