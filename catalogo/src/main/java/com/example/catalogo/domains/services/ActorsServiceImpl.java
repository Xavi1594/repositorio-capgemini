package com.example.catalogo.domains.services;

import java.util.List;
import java.util.Optional;

import com.example.catalogo.domains.contracts.repositories.ActorRepository;
import com.example.catalogo.domains.contracts.services.ActorsService;
import com.example.catalogo.entities.Actor;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

public class ActorsServiceImpl implements ActorsService {
    private ActorRepository dao;

    public ActorsServiceImpl(ActorRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Actor> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Actor> getOne(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("El actor no puede ser nulo");
        }
        if (item.isInvalid()) {
            throw new InvalidDataException(item.getErrorsMessage());
        }

        if (item.getActorId() > 0 && dao.existsById(item.getActorId())) {
            throw new DuplicateKeyException("El actor ya existe");
        }
        return dao.save(item);
    }

    @Override
    public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("El actor no puede ser nulo");
        }
        if (item.isInvalid()) {
            throw new InvalidDataException(item.getErrorsMessage());
        }

        if (!dao.existsById(item.getActorId())) {
            throw new NotFoundException("El actor no existe");
        }
        return dao.save(item);
    }

    @Override
    public void delete(Actor item) throws InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("El actor no puede ser nulo");
        }
        // if(dao.existsById(item.getActorId())) {
        // throw new NotFoundException("El actor no existe");
        // }
        dao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {
        // if(dao.existsById(id)) {
        // throw new NotFoundException("El actor no existe");
        // }
        dao.deleteById(id);
    }
}
