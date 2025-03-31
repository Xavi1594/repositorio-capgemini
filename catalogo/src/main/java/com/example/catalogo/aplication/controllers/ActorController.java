package com.example.catalogo.aplication.controllers;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.catalogo.aplication.contracts.CatalogoService;
import com.example.catalogo.aplication.models.NewsDTO;
import com.example.catalogo.domains.contracts.services.ActorsService;
import com.example.catalogo.domains.entities.models.ActorDTO;
import com.example.catalogo.exceptions.BadRequestException;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/actors/v1")
@Tag(name = "Actors", description = "Actors API")
class ActorController {
    private final ActorsService actorService;
    @Autowired
    private CatalogoService catalogoService;

    public ActorController(ActorsService actorService) {
        super();
        this.actorService = actorService;
    }

    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.getByProjection(ActorDTO.class);
    }

    @GetMapping(path = "/{id}")
    @Operation(description = "Get one entity by id")
    public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
        var item = actorService.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return ActorDTO.from(item.get());
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Entity created")
    @Operation(description = "Create a new entity")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = actorService.add(ActorDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(params = { "page" })
    @Operation(summary = "Get a page of entities")
    public Page<ActorDTO> getAll(@ParameterObject Pageable pageable) {
        return actorService.getByProjection(pageable, ActorDTO.class);
    }

    @GetMapping("/new")
    @Operation(description = "Get new actors")
    @ApiResponse(responseCode = "200", description = "Actors found")
    public List<ActorDTO> getNewActors(@RequestParam(required = false) Long daysAgo) {
        if (daysAgo == null) {
            daysAgo = 2L;
        }

        Timestamp lastUpdate = Timestamp.valueOf(LocalDate.now().minusDays(daysAgo).atStartOfDay());

        NewsDTO news = catalogoService.newsDTO(lastUpdate);

        return news.getActors();
    }

    record Title(int id, String titulo) {
    }

    @GetMapping("path" + "/{id}/movies")
    @Operation(description = "Get movies by actor")
    @ApiResponse(responseCode = "200", description = "Movies found")
    @Transactional
    public List<Title> getMovies(@PathVariable int id) throws NotFoundException {
        var item = actorService.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return item.get().getFilmActors().stream()
                .map(o -> new Title(o.getFilm().getFilmId(), o.getFilm().getTitle()))
                .toList();
    }

    @PutMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Entity updated")
    @Operation(description = "Update an entity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getActorId() != id) {
            throw new BadRequestException("El id del actor no coincide con el recurso a modificar");
        }
        actorService.modify(ActorDTO.from(item));
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204", description = "Entity deleted")
    @Operation(description = "Delete an entity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        actorService.deleteById(id);
    }
}