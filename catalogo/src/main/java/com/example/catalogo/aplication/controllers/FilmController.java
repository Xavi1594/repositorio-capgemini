package com.example.catalogo.aplication.controllers;

import java.net.URI;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.domains.contracts.services.FilmService;
import com.example.catalogo.domains.entities.Film;
import com.example.catalogo.domains.entities.models.FilmDetailsDTO;
import com.example.catalogo.domains.entities.models.FilmEditDTO;
import com.example.catalogo.domains.entities.models.FilmShortDTO;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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

    @Operation(summary = "Añadir una nueva pelicula")
    @ApiResponse(responseCode = "201", description = "Pelicula añadida")
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<Object> add(@RequestBody @Valid FilmShortDTO item)
            throws DuplicateKeyException, InvalidDataException {
        // Convertir el DTO en una entidad Film antes de pasar al servicio
        Film filmEntity = FilmShortDTO.from(item);
        var newItem = filmService.add(filmEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getFilmId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = { "page" })
    @Operation(description = "Get a page of entities")
    public Page<FilmShortDTO> getAll(@ParameterObject Pageable pageable) {
        return filmService.getByProjection(pageable, FilmShortDTO.class);
    }

    @PutMapping(path = "/{id}")
    @Operation(description = "Modify one entity by id")
    @ApiResponse(responseCode = "200", description = "Entity modified")
    public void modify(@PathVariable int id, @Valid @RequestBody FilmEditDTO item)
            throws NotFoundException, InvalidDataException {
        var film = filmService.getOne(id);
        if (film.isEmpty()) {
            throw new NotFoundException("No se encontró la pelicula con id " + id);
        }
        filmService.modify(FilmEditDTO.from(item));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(description = "Delete one entity by id")
    @ApiResponse(responseCode = "204", description = "Entity deleted")
    public void delete(@PathVariable int id) throws NotFoundException {
        var item = filmService.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró la pelicula con id " + id);
        }
        filmService.deleteById(id);
    }
}