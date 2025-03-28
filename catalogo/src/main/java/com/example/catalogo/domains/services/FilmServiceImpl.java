package com.example.catalogo.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.catalogo.domains.contracts.repositories.FilmRepository;
import com.example.catalogo.domains.contracts.services.FilmService;
import com.example.catalogo.domains.entities.Film;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FilmServiceImpl implements FilmService {
    private FilmRepository dao;

    public FilmServiceImpl(FilmRepository dao) {
        this.dao = dao;
    }

    @Override
    public <T> List<T> getByProjection(Class<T> type) {
        return dao.findAllBy(type);
    }

    @Override
    public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
        return dao.findAllBy(sort, type);
    }

    @Override
    public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
        return dao.findAll(pageable, type);
    }

    @Override
    public List<Film> getAll(@NonNull Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    public Page<Film> getAll(@NonNull Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public List<Film> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Film> getOne(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Optional<Film> getOne(@NonNull Specification<Film> spec) {
        return dao.findOne(spec);
    }

    @Override
    public List<Film> getAll(@NonNull Specification<Film> spec) {
        return dao.findAll(spec);
    }

    @Override
    public Page<Film> getAll(@NonNull Specification<Film> spec, @NonNull Pageable pageable) {
        return dao.findAll(spec, pageable);
    }

    @Override
    public List<Film> getAll(@NonNull Specification<Film> spec, @NonNull Sort sort) {
        return dao.findAll(spec, sort);
    }

    @Override
    @Transactional
    public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
        // Validar que el objeto no es nulo
        if (item == null) {
            throw new InvalidDataException("El objeto no puede ser nulo");
        }

        // Validar que el objeto tiene datos válidos
        if (item.isInvalid()) {
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        }

        // Verificar si ya existe una película con el mismo ID
        if (dao.existsById(item.getFilmId())) {
            throw new DuplicateKeyException("Ya existe una película con el ID " + item.getFilmId());
        }

        // Guardar la película en la base de datos
        return dao.save(item);
    }

    @Override
    @Transactional
    public Film modify(Film item) throws NotFoundException, InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        var leido = dao.findById(item.getFilmId()).orElseThrow(() -> new NotFoundException());
        return dao.save(item.merge(leido));
    }

    @Override
    public void delete(Film item) throws InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        deleteById(item.getFilmId());
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public List<Film> newsDTO(@NonNull Timestamp lastUpdate) {
        return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(lastUpdate);
    }
}
