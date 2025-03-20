package com.example.catalogo.aplication.controllers;

import java.net.URI;
import java.util.List;

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

import com.example.catalogo.domains.contracts.services.ActorsService;
import com.example.catalogo.domains.entities.models.ActorDTO;
import com.example.catalogo.exceptions.BadRequestException;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/actors/v1")
class ActorController {
    private final ActorsService actorService;

    public ActorController(ActorsService actorService) {
        super();
        this.actorService = actorService;
    }

    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.getByProjection(ActorDTO.class);
    }

    @GetMapping(path = "/{id}")
    public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
        var item = actorService.getOne(id);
        if (item.isEmpty()) {
            throw new NotFoundException("No se encontró el actor con id " + id);
        }
        return ActorDTO.from(item.get());
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item)
            throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = actorService.add(ActorDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item)
            throws BadRequestException, NotFoundException, InvalidDataException {
        if (item.getActorId() != id) {
            throw new BadRequestException("El id del actor no coincide con el recurso a modificar");
        }
        actorService.modify(ActorDTO.from(item));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        actorService.deleteById(id);
    }
}