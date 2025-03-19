package com.example.catalogo.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.catalogo.domains.contracts.repositories.CategoryRepository;
import com.example.catalogo.domains.contracts.services.CategoriesService;
import com.example.catalogo.domains.entities.Category;
import com.example.catalogo.exceptions.DuplicateKeyException;
import com.example.catalogo.exceptions.InvalidDataException;
import com.example.catalogo.exceptions.NotFoundException;

@Service
public class CategoryServiceImpl implements CategoriesService {
    private CategoryRepository dao;

    public CategoryServiceImpl(CategoryRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Category> getAll() {
        return dao.findAllByOrderByName();
    }

    @Override
    public Optional<Category> getOne(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Category add(Category item) throws DuplicateKeyException, InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        if (item.getCategoryId() != 0 && dao.existsById(item.getCategoryId()))
            throw new DuplicateKeyException("Ya existe");
        return dao.save(item);
    }

    @Override
    public Category modify(Category item) throws NotFoundException, InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        if (!dao.existsById(item.getCategoryId()))
            throw new NotFoundException();
        return dao.save(item);
    }

    @Override
    public void delete(Category item) throws InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        dao.delete(item);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public List<Category> newsDTO(Timestamp lastUpdate) {
        return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(lastUpdate);
    }

}