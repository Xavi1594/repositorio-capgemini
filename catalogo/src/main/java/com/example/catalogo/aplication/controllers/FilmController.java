package com.example.catalogo.aplication.controllers;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.domains.contracts.services.FilmService;
import com.example.catalogo.domains.entities.Actor;
import com.example.catalogo.domains.entities.models.FilmDetailsDTO;
import com.example.catalogo.domains.entities.models.FilmShortDTO;
import com.example.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/films/v1")
@Tag(name = "Films", description = "Films API")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        super();
        this.filmService = filmService;
    }

    @GetMapping
    public List<FilmShortDTO> getAllFilms() {
        return filmService.getByProjection(FilmShortDTO.class);
    }

    @GetMapping(path = "/{id}")
    @Operation(description = "Get one entity by id")
    public FilmDetailsDTO getOne(@PathVariable int id) throws NotFoundException {
        var item = filmService.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró la pelicula con id " + id);
        }
        return FilmDetailsDTO.from(item.get());
    }

    // @PostMapping
    // @ApiResponse(responseCode = "201", description = "Entity created")
    // @Operation(description = "Create a new entity")
    // @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    // public ResponseEntity<Object> create(
    // @Valid @PathVariable FilmShortDTO item) {
    // var newItem = filmService.add(FilmShortDTO.from(item));
    // return
    // ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
    // .buildAndExpand(newItem.getFilmId()).toUri()).body(FilmShortDTO.from(newItem));
    // }

    @GetMapping(params = { "page" })
    @Operation(description = "Get a page of entities")
    public Page<FilmShortDTO> getAll(@ParameterObject Pageable pageable) {
        return filmService.getByProjection(pageable, FilmShortDTO.class);
    }

}
