package com.example.catalogo.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.catalogo.domains.contracts.repositories.ActorRepository;
import com.example.catalogo.domains.contracts.services.ActorsService;
import com.example.catalogo.domains.entities.Actor;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

@Service
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

    @Override
    public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
        return dao.findAllBy(sort, type);
    }

    @Override
    public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
        return dao.findAllBy(pageable, type);
    }

    @Override
    public Iterable<Actor> getAll(Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    public Page<Actor> getAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public List<Actor> NewsDTO(Timestamp lastUpdate) {
        return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(lastUpdate);
    }

    @Override
    public <T> List<T> getByProjection(Class<T> type) {
        return dao.findAllBy(type);
    }

    @Override
    public Optional<Actor> getOne(Specification<Actor> spec) {
        return dao.findOne(spec);
    }

    @Override
    public List<Actor> getAll(Specification<Actor> spec) {
        return dao.findAll(spec);
    }

    @Override
    public Page<Actor> getAll(Specification<Actor> spec, Pageable pageable) {
        return dao.findAll(spec, pageable);
    }

    @Override
    public List<Actor> getAll(Specification<Actor> spec, Sort sort) {
        return dao.findAll(spec, sort);
    }
}
